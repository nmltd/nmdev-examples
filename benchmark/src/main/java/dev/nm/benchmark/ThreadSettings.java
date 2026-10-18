package dev.nm.benchmark;

/** One computational worker by default; configure each fork explicitly for scaling runs. */
final class ThreadSettings {
    static int count() {
        int n=Integer.getInteger("benchmark.threads",1);
        if(n<1)throw new IllegalArgumentException("benchmark.threads must be positive");
        return n;
    }
    private ThreadSettings() {}
}
