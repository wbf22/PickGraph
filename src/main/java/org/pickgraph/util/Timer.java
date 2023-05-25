package org.pickgraph.util;

public class Timer {

    private long time;

    public Timer() {
        time = System.currentTimeMillis();
    }

    public static Timer start() {
        return new Timer();
    }

    public long stop() {
        return System.currentTimeMillis() - time;
    }

    public long stopAndPrint(String label) {
        long time = stop();
        System.out.println(label + ": " + time);
        return time;
    }

}
