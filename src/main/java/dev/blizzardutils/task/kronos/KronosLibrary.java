package dev.blizzardutils.task.kronos;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class KronosLibrary<K> {

    private CompletableFuture<K> completableFuture;

    public KronosLibrary<K> createNewChain(K value) {
        completableFuture = CompletableFuture.supplyAsync(() -> value);
        return this;
    }

    public KronosLibrary<K> handleSync(BiFunction<K, Throwable, Object> biFunction) {
        completableFuture.handle(biFunction);
        return this;
    }

    public KronosLibrary<K> handleAsync(BiFunction<K, Throwable, Object> biFunction) {
        completableFuture.handleAsync(biFunction);
        return this;
    }

    public KronosLibrary<K> acceptSync(Consumer<? super K> consumer) {
        completableFuture.thenAccept(consumer);
        return this;
    }

    public KronosLibrary<K> acceptAsync(Consumer<? super K> consumer) {
        completableFuture.thenAcceptAsync(consumer);
        return this;
    }

    public KronosLibrary<K> runSync(Runnable runnable) {
        completableFuture.thenRun(runnable);
        return this;
    }

    public KronosLibrary<K> runAsync(Runnable runnable) {
        completableFuture.thenRunAsync(runnable);
        return this;
    }

    public KronosLibrary<K> whenCompleteSync(BiConsumer<K, Throwable> biConsumer) {
        completableFuture.whenComplete(biConsumer);
        return this;
    }

    public KronosLibrary<K> whenCompleteAsync(BiConsumer<K, Throwable> biConsumer) {
        completableFuture.whenCompleteAsync(biConsumer);
        return this;
    }

    public KronosLibrary<K> callBukkitSyncTask(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
        return this;
    }

    public KronosLibrary<K> callBukkitAsyncTask(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        return this;
    }

    public KronosLibrary<K> callBukkitDelayedSyncTask(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        return this;
    }

    public KronosLibrary<K> callBukkitDelayedAsyncTask(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
        return this;
    }
}
