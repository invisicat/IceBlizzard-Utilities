package dev.blizzardutils.task.kronos;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class KronosChain<K> {

    private KronosChain<K> chain;
    private CompletableFuture<K> completableFuture;

    public KronosChain<K> createCompletedChain() {
        completableFuture = CompletableFuture.completedFuture(null);
        return this;
    }


    public KronosChain<K> createNewChain(K value) {
        completableFuture = CompletableFuture.completedFuture(value);
        return this;
    }

    public KronosChain<K> createNewChainSupplied(K value) {
        completableFuture = CompletableFuture.supplyAsync(() -> value);
        return this;
    }

    public KronosChain<K> createNewChainWithExecutorService(K value, ExecutorService executorService) {
        completableFuture = CompletableFuture.supplyAsync(() -> value, executorService);
        return this;
    }

    public KronosChain<K> handleSync(BiFunction<K, Throwable, Object> biFunction) {
        completableFuture.handle(biFunction);
        return this;
    }

    public KronosChain<K> handleAsync(BiFunction<K, Throwable, Object> biFunction) {
        completableFuture.handleAsync(biFunction);
        return this;
    }

    public KronosChain<K> acceptSync(Consumer<? super K> consumer) {
        completableFuture.thenAccept(consumer);
        return this;
    }

    public KronosChain<K> acceptAsync(Consumer<? super K> consumer) {
        completableFuture.thenAcceptAsync(consumer);
        return this;
    }

    public KronosChain<K> applySync(Function<? super K, ? extends K> function) {
        completableFuture.thenApply(function);
        return this;
    }

    public KronosChain<K> applyAsync(Function<? super K, ? extends K> function) {
        completableFuture.thenApplyAsync(function);
        return this;
    }

    public KronosChain<K> combineSync(CompletionStage<? extends K> completionStage, BiFunction<? super K, ? super K, ? extends K> biFunction) {
        completableFuture.thenCombine(completionStage, biFunction);
        return this;
    }

    public KronosChain<K> combineAsync(CompletionStage<? extends K> completionStage, BiFunction<? super K, ? super K, ? extends K> biFunction) {
        completableFuture.thenCombineAsync(completionStage, biFunction);
        return this;
    }

    public KronosChain<K> runSync(Runnable runnable) {
        completableFuture.thenRun(runnable);
        return this;
    }

    public KronosChain<K> runAsync(Runnable runnable) {
        completableFuture.thenRunAsync(runnable);
        return this;
    }

    public KronosChain<K> whenCompleteSync(BiConsumer<K, Throwable> biConsumer) {
        completableFuture.whenComplete(biConsumer);
        return this;
    }

    public KronosChain<K> whenCompleteAsync(BiConsumer<K, Throwable> biConsumer) {
        completableFuture.whenCompleteAsync(biConsumer);
        return this;
    }


    public KronosChain<K> throwExceptionally(Function<Throwable, ? extends K> function) {
        completableFuture.exceptionally(function);
        return this;
    }

    public KronosChain<K> completeExceptionally(Throwable throwable) {
        completableFuture.completeExceptionally(throwable);
        return this;
    }


    public KronosChain<K> cancel() {
        completableFuture.cancel(true);
        //done();
        return this;
    }

    public Boolean isCancelled() {
        return completableFuture.isCancelled();
    }

    public KronosChain<K> done() {
        assert completableFuture.isDone() : "Completable Future is not done running!";
        return this;
    }

    public KronosChain<K> callBukkitSyncTask(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
        return this;
    }

    public KronosChain<K> callBukkitAsyncTask(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        return this;
    }

    public KronosChain<K> callBukkitDelayedSyncTask(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        return this;
    }

    public KronosChain<K> callBukkitDelayedAsyncTask(Plugin plugin, Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
        return this;
    }

    public KronosChain<K> callSyncMethod(Plugin plugin, Callable<K> callable) {
        Bukkit.getScheduler().callSyncMethod(plugin, callable);
        return this;
    }

    public KronosChain<K> callSyncTimer(Plugin plugin, Runnable runnable, long l1, long l2) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, l1, l2);
        return this;
    }

    public KronosChain<K> callAsyncTimer(Plugin plugin, Runnable runnable, long l1, long l2) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, l1, l2);
        return this;
    }
}