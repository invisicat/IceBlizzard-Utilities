package dev.blizzardutils.kronos.util;

import org.bukkit.Bukkit;

public enum ThreadContext {

    SYNC(), ASYNC();

    private static Thread mainThread = null;

    public static ThreadContext getContext(Thread thread) {
        boolean isItMainThread = mainThread == null && Bukkit.getServer().isPrimaryThread();
        if (isItMainThread) {
            mainThread = Thread.currentThread();
        }

        return thread == mainThread ? SYNC : ASYNC;
    }
}
