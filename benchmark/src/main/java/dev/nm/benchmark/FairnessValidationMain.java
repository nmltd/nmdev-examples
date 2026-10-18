package dev.nm.benchmark;

import org.ojalgo.matrix.store.R064Store;
import org.ojalgo.matrix.store.RawStore;

/** Regression check: numerical validation alone must not accept deferred timed work. */
public final class FairnessValidationMain {
    public static void main(String[] args) {
        Inputs in=new Inputs(4);
        R064Store a=R064Store.FACTORY.copy(RawStore.wrap(in.a));
        boolean rejected=false;
        try {Validation.assertMaterialized(a.multiply(1.5));}catch(IllegalStateException expected){rejected=true;}
        if(!rejected)throw new IllegalStateException("Deferred output guard failed");
        Validation.checkRepeated("scale",in,new OjalgoAdapter(in));
        // Ensure the mutation guard also rejects a genuinely changed native input.
        double[][] changed=Validation.identity(4);changed[0][0]=2;
        rejected=false;try {Validation.exact(Validation.identity(4),changed,"mutation");}catch(IllegalStateException expected){rejected=true;}
        if(!rejected)throw new IllegalStateException("Mutation guard failed");
        System.out.println("Deferred-output guard and materialized scaling: PASS");
    }
}
