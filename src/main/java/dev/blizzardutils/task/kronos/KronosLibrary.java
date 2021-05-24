package dev.blizzardutils.task.kronos;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class KronosLibrary<K> {

    private CompletableFuture<K> completableFuture;

    public KronosLibrary<K> createNewChain(K value) {
        completableFuture = CompletableFuture.completedFuture(value);
        return this;
    }

    public KronosLibrary<K> createNewChainSupplied(K value) {
        completableFuture = CompletableFuture.supplyAsync(() -> value);
        return this;
    }

    public KronosLibrary<K> createNewChainWithExecutorService(K value, ExecutorService executorService) {
        completableFuture = CompletableFuture.supplyAsync(() -> value, executorService);
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

    public KronosLibrary<K> applySync(Function<? super K, ? extends K> function) {
        completableFuture.thenApply(function);
        return this;
    }

    public KronosLibrary<K> applyAsync(Function<? super K, ? extends K> function) {
        completableFuture.thenApplyAsync(function);
        return this;
    }

    public KronosLibrary<K> combineSync(CompletionStage<? extends K> completionStage, BiFunction<? super K, ? super K, ? extends K> biFunction) {
        completableFuture.thenCombine(completionStage, biFunction);
        return this;
    }

    public KronosLibrary<K> combineAsync(CompletionStage<? extends K> completionStage, BiFunction<? super K, ? super K, ? extends K> biFunction) {
        completableFuture.thenCombineAsync(completionStage, biFunction);
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


    public KronosLibrary<K> throwExceptionally(Function<Throwable, ? extends K> function) {
        completableFuture.exceptionally(function);
        return this;
    }

    public KronosLibrary<K> completeExceptionally(Throwable throwable) {
        completableFuture.completeExceptionally(throwable);
        return this;
    }


    public KronosLibrary<K> cancel() {
        completableFuture.cancel(true);
        done();
        return this;
    }

    public KronosLibrary<K> done() {
        assert completableFuture.isDone() : "Completable Future is not done running!";
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

    public KronosLibrary<K> callSyncMethod(Plugin plugin, Callable<K> callable) {
        Bukkit.getScheduler().callSyncMethod(plugin, callable);
        return this;
    }

    public KronosLibrary<K> callSyncTimer(Plugin plugin, Runnable runnable, long l1, long l2) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, l1, l2);
        return this;
    }

    public KronosLibrary<K> callAsyncTimer(Plugin plugin, Runnable runnable, long l1, long l2) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, l1, l2);
        return this;
    }
}