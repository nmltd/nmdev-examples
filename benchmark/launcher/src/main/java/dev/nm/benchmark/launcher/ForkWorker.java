package dev.nm.benchmark.launcher;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.openjdk.jmh.results.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.CommandLineOptions;

/** Internal child entry point: persists JMH's own result objects for exact aggregation. */
public final class ForkWorker {
    private ForkWorker() {}
    public static void main(String[] args) throws Exception {
        Path binary = Path.of(args[0]);
        var results = new Runner(new CommandLineOptions(Arrays.copyOfRange(args, 1, args.length))).run();
        if (results.size() != 1) throw new IOException("Expected exactly one case per checkpointed fork");
        Path temp = binary.resolveSibling(binary.getFileName() + ".tmp");
        try (FileOutputStream file = new FileOutputStream(temp.toFile()); ObjectOutputStream stream = new ObjectOutputStream(file)) {
            stream.writeObject(new ArrayList<>(results)); stream.flush(); file.getFD().sync();
        }
        Storage.move(temp, binary);
    }
    @SuppressWarnings("unchecked")
    static RunResult readOwnCheckpoint(Path path) throws Exception {
        // Only our verified local checkpoint is deserialized. Shared ZIPs are imported as JSON.
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            in.setObjectInputFilter(info -> info.depth() > 100 || info.references() > 2_000_000 || info.arrayLength() > 2_000_000 ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED);
            List<RunResult> results = (List<RunResult>) in.readObject();
            if (results.size() != 1) throw new IOException("Invalid fork checkpoint");
            return results.get(0);
        }
    }
}
