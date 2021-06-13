package dev.blizzardutils.kronos.chain;

import dev.blizzardutils.kronos.util.ThreadContext;
import dev.blizzardutils.main.BlizzardUtilsMain;

public class KronosRunMethods {

    private BlizzardUtilsMain plugin;

    public KronosRunMethods(BlizzardUtilsMain plugin) {
        this.plugin = plugin;
    }

    public void runTask(Runnable runnable, ThreadContext context, long delay) {
        if (delay > 0) {
            runTaskLater(runnable, context, delay);
        } else {
            switch (context) {
                case SYNC:
                    if (ThreadContext.getContext(Thread.currentThread()) == ThreadContext.SYNC) {
                        runnable.run();
                    } else {
                        plugin.getServer().getScheduler().runTask(plugin, runnable);
                    }
                    break;
                case ASYNC:
                    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
                    break;
                default:
                    throw new IllegalStateException("Context Issue: " + context);
            }
        }
    }

    private void runTaskLater(Runnable runnable, ThreadContext context, long delay) {
        switch (context) {
            case SYNC:
                plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
                break;
            case ASYNC:
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
                break;
            default:
                throw new IllegalStateException("Context Issue: " + context);
        }
    }
}
