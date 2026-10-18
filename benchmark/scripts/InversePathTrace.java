import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import java.util.*;

/** Untimed JDI observation: no instrumentation, replacement classes or changes to the measured JAR. */
public final class InversePathTrace {
    public static void main(String[] args) throws Exception {
        boolean general=args.length>0&&args[0].equals("general");
        LaunchingConnector connector=Bootstrap.virtualMachineManager().defaultConnector();
        Map<String,Connector.Argument> config=connector.defaultArguments();
        config.get("main").setValue("dev.nm.benchmark.InversePathProbe "+(general?"general":"spd")+" "+(args.length>1?Integer.parseInt(args[1]):64));
        config.get("options").setValue("-XX:ActiveProcessorCount=1 -cp \""+System.getProperty("java.class.path")+"\"");
        VirtualMachine vm=connector.launch(config);
        String chol="dev.nm.algebra.linear.matrix.doubles.factorization.triangle.cholesky.Chol";
        String lu="dev.nm.algebra.linear.matrix.doubles.factorization.triangle.LU";
        for(String name:new String[]{chol,lu}) {
            ClassPrepareRequest request=vm.eventRequestManager().createClassPrepareRequest();
            request.addClassFilter(name);request.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);request.enable();
        }
        int choleskyConstructors=0,luConstructors=0;boolean done=false;
        while(!done) {
            EventSet events=vm.eventQueue().remove(30000);
            if(events==null){vm.dispose();throw new IllegalStateException("Debuggee timed out");}
            for(Event event:events) {
                if(event instanceof ClassPrepareEvent) {
                    ReferenceType type=((ClassPrepareEvent)event).referenceType();
                    for(Method method:type.methodsByName("<init>")) {
                        if(!method.declaringType().name().equals(type.name()))continue;
                        BreakpointRequest request=vm.eventRequestManager().createBreakpointRequest(method.location());
                        request.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);request.enable();
                    }
                }
                if(event instanceof BreakpointEvent) {
                    BreakpointEvent entry=(BreakpointEvent)event;
                    if(entry.location().method().name().equals("<init>")) {
                        String owner=entry.location().method().declaringType().name();
                        if(owner.equals(chol))choleskyConstructors++;
                        if(owner.equals(lu))luConstructors++;
                        System.out.println(owner+entry.location().method().signature());
                        for(StackFrame frame:entry.thread().frames())System.out.println("  "+frame.location().declaringType().name()+"."+frame.location().method().name());
                    }
                }
                if(event instanceof VMDeathEvent||event instanceof VMDisconnectEvent)done=true;
            }
            try{events.resume();}catch(VMDisconnectedException ignored){}
        }
        Process process=vm.process();int exit=process.waitFor();
        System.out.write(process.getInputStream().readAllBytes());System.err.write(process.getErrorStream().readAllBytes());
        System.out.println("Chol constructors="+choleskyConstructors+", LU constructors="+luConstructors+", exit="+exit);
        if(exit!=0 || (general?(luConstructors==0||choleskyConstructors!=0):(choleskyConstructors==0||luConstructors!=0)))throw new IllegalStateException("Unexpected inverse execution path");
    }
}
