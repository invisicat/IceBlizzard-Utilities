package dev.blizzardutils.kronos.chain;

import dev.blizzardutils.kronos.util.ThreadContext;
import dev.blizzardutils.main.BlizzardUtilsMain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class KronosChain<K> {


    private KronosRunMethods instance = BlizzardUtilsMain.getInstance().getKronosRunMethods();
    private final CompletableFuture<K> completableFuture;
    private final AtomicBoolean hasBeenSupplied = new AtomicBoolean(false);
    private final AtomicBoolean isCancelled = new AtomicBoolean(false);
    private final ThreadContext SYNC = ThreadContext.SYNC;
    private final ThreadContext ASYNC = ThreadContext.ASYNC;

    private KronosChain(CompletableFuture<K> completableFuture) {
        this.completableFuture = completableFuture;
    }

    private KronosChain(CompletableFuture<K> completableFuture, boolean supplied, boolean cancelled) {
        this(completableFuture);

        this.hasBeenSupplied.set(supplied);
        this.isCancelled.set(cancelled);
    }

    public static <K> KronosChain<K> createCompletedKronosChain() {
        KronosChain<K> kronosChain = new KronosChain<>(CompletableFuture.completedFuture(null));
        kronosChain.hasBeenSupplied.set(true);

        return kronosChain;
    }

    public static <K> KronosChain<K> createCompletedKronosChain(K value) {
        KronosChain<K> kronosChain = new KronosChain<>(CompletableFuture.completedFuture(value));
        kronosChain.hasBeenSupplied.set(true);

        return kronosChain;
    }

    public static <K> KronosChain<K> createKronosChain() {
        return new KronosChain<>(new CompletableFuture<>());
    }

    public static <K> KronosChain<K> createSupplyingSyncKronosChain(Supplier<K> supplier) {
        KronosChain<K> kronosChain = createKronosChain();

        return kronosChain.supplySync(supplier);
    }

    public static <K> KronosChain<K> createSupplyingSyncDelayedKronosChain(Supplier<K> supplier, long delay) {
        KronosChain<K> kronosChain = createKronosChain();

        return kronosChain.supplySyncDelayed(supplier, delay);
    }

    public static <K> KronosChain<K> createSupplyingAsyncKronosChain(Supplier<K> supplier) {
        KronosChain<K> kronosChain = createKronosChain();

        return kronosChain.supplyAsync(supplier);
    }

    public static <K> KronosChain<K> createSupplyingAsyncDelayedKronosChain(Supplier<K> supplier, long delay) {
        KronosChain<K> kronosChain = createKronosChain();

        return kronosChain.supplyAsyncDelayed(supplier, delay);
    }


    // ASYNC BELOW:

    public KronosChain<K> supplyAsync(Supplier<K> supplier) {
        return supply(supplier, ASYNC, 0);
    }

    public KronosChain<Void> runAsync(Runnable runnable) {
        return applyRun(runnable, ASYNC, 0);
    }

    public <C> KronosChain<C> applyAsync(Function<? super K, ? extends C> function) {
        return apply(function, ASYNC, 0);
    }

    public KronosChain<Void> acceptAsync(Consumer<K> consumer) {
        return accept(consumer, ASYNC, 0);
    }

    public KronosChain<K> exceptionallyAsync(Function<Throwable, ? extends K> function) {
        return exceptionally(function, ASYNC, 0);
    }


    public <C> KronosChain<C> composeAsync(Function<? super K, ? extends KronosChain<C>> function) {
        return compose(function, ASYNC, 0);
    }

    // DELAYED ASYNC BELOW

    public KronosChain<K> supplyAsyncDelayed(Supplier<K> supplier, long delay) {
        return supply(supplier, ASYNC, delay);
    }

    public KronosChain<Void> runAsyncDelayed(Runnable runnable, long delay) {
        return applyRun(runnable, ASYNC, delay);
    }


    public <C> KronosChain<C> applyAsyncDelayed(Function<? super K, ? extends C> function, long delay) {
        return apply(function, ASYNC, delay);
    }

    public KronosChain<Void> acceptAsyncDelayed(Consumer<K> consumer, long delay) {
        return accept(consumer, ASYNC, delay);
    }

    public KronosChain<K> exceptionallyAsyncDelayed(Function<Throwable, ? extends K> function, long delay) {
        return exceptionally(function, ASYNC, delay);
    }


    public <C> KronosChain<C> composeAsyncDelayed(Function<? super K, ? extends KronosChain<C>> function, long delay) {
        return compose(function, ASYNC, delay);
    }


    // SYNC BELOW:

    public KronosChain<K> supplySync(Supplier<K> supplier) {
        return supply(supplier, SYNC, 0);
    }


    public KronosChain<Void> runSync(Runnable runnable) {
        return applyRun(runnable, SYNC, 0);
    }

    public <C> KronosChain<C> applySync(Function<? super K, ? extends C> function) {
        return apply(function, SYNC, 0);
    }

    public KronosChain<Void> acceptSync(Consumer<K> consumer) {
        return accept(consumer, SYNC, 0);
    }

    public KronosChain<K> exceptionallySync(Function<Throwable, ? extends K> function) {
        return exceptionally(function, SYNC, 0);
    }


    public <C> KronosChain<K> composeSync(Function<? super K, ? extends KronosChain<K>> function) {
        return compose(function, SYNC, 0);
    }

    // DELAYED SYNC BELOW

    public KronosChain<K> supplySyncDelayed(Supplier<K> supplier, long delay) {
        return supply(supplier, SYNC, delay);
    }

    public KronosChain<Void> runSyncDelayed(Runnable runnable, long delay) {
        return applyRun(runnable, SYNC, delay);
    }

    public <C> KronosChain<C> applySyncDelayed(Function<? super K, ? extends C> function, long delay) {
        return apply(function, SYNC, delay);
    }

    public KronosChain<Void> acceptSyncDelayed(Consumer<K> consumer, long delay) {
        return accept(consumer, SYNC, delay);
    }


    public KronosChain<K> exceptionallySyncDelayed(Function<Throwable, ? extends K> function, long delay) {
        return exceptionally(function, SYNC, delay);
    }

    public <C> KronosChain<K> composeSyncDelayed(Function<? super K, ? extends KronosChain<K>> function, long delay) {
        return compose(function, SYNC, delay);
    }

    public void complete(K value) {
        if (!isCancelled.get()) {
            completableFuture.complete(value);
        }
    }

    public void completeExceptionally(Throwable throwable) {
        if (!isCancelled.get()) {
            completableFuture.completeExceptionally(throwable);
        }
    }


    public CompletableFuture<K> toCompletableFuture() {
        return completableFuture.thenApply(Function.identity());
    }


    private void setHasBeenSupplied() {
        if (!hasBeenSupplied.compareAndSet(false, true)) {
            throw new AssertionError("This can only be supplied once, and this KronosChain has already been supplied");
        }
    }


    private KronosChain<K> supply(Supplier<K> supplier, ThreadContext threadContext, long delay) {
        setHasBeenSupplied();
        KronosRunnables.SupplierRunnable<K> supplierRunnable = new KronosRunnables.SupplierRunnable<>(this, supplier);
        instance.runTask(supplierRunnable, threadContext, delay);
        return this;
    }

    private <C> KronosChain<C> apply(Function<? super K, ? extends C> function, ThreadContext threadContext, long delay) {
        KronosChain<C> kronosChain = new KronosChain<>(new CompletableFuture<>());

        completableFuture.whenComplete((value, throwable) -> {
            if (throwable == null) {
                KronosRunnables.FunctionRunnable<C, K> functionRunnable = new KronosRunnables.FunctionRunnable<C, K>(kronosChain, function, value);
                instance.runTask(functionRunnable, threadContext, delay);
            } else {
                kronosChain.completeExceptionally(throwable);
            }
        });

        return kronosChain;
    }

    private <K> KronosChain<K> applyRun(Runnable runnable, ThreadContext threadContext, long delay) {
        KronosChain<K> kronosChain = new KronosChain<>(new CompletableFuture<>());

        completableFuture.whenComplete((value, throwable) -> {
            if (throwable == null) {
                KronosRunnables.WrappedRunnable<K> wrappedRunnable = new KronosRunnables.WrappedRunnable<>(kronosChain, runnable);
                instance.runTask(wrappedRunnable, threadContext, delay);
            } else {
                kronosChain.completeExceptionally(throwable);
            }
        });

        return kronosChain;
    }

    <C> KronosChain<C> accept(Consumer<K> consumer, ThreadContext threadContext, long delay) {
        KronosChain<C> kronosChain = new KronosChain<>(new CompletableFuture<>());
        completableFuture.whenComplete((value, throwable) -> {
            if (throwable == null) {
                KronosRunnables.ConsumerRunnable<C, K> consumerRunnable = new KronosRunnables.ConsumerRunnable<>(kronosChain, consumer, value);
                instance.runTask(consumerRunnable, threadContext, delay);
            } else {
                kronosChain.completeExceptionally(throwable);
            }
        });

        return kronosChain;
    }

    private KronosChain<K> exceptionally(Function<Throwable, ? extends K> function, ThreadContext threadContext, long delay) {
        KronosChain<K> kronosChain = new KronosChain<>(new CompletableFuture<>());
        completableFuture.whenComplete((value, throwable) -> {
            if (throwable == null) {
                kronosChain.complete(value);
            } else {
                KronosRunnables.FunctionRunnable<K, Throwable> functionRunnable = new KronosRunnables.FunctionRunnable<>(kronosChain, function, throwable);
                instance.runTask(functionRunnable, threadContext, delay);
            }
        });

        return kronosChain;
    }

    private <C> KronosChain<C> compose(Function<? super K, ? extends KronosChain<C>> function, ThreadContext threadContext, long delay) {
        KronosChain<C> kronosChain = new KronosChain<>(new CompletableFuture<>());
        completableFuture.whenComplete((value, throwable) -> {

            if (throwable == null) {
                KronosRunnables.ComposeRunnable<C, K> composeRunnable = new KronosRunnables.ComposeRunnable<>(kronosChain, function, value, threadContext);
                instance.runTask(composeRunnable, threadContext, delay);
            } else {
                kronosChain.completeExceptionally(throwable);
            }
        });
        return kronosChain;
    }

    public AtomicBoolean getIsCancelled() {
        return isCancelled;
    }

    public AtomicBoolean getHasBeenSupplied() {
        return hasBeenSupplied;
    }

    CompletableFuture<K> getCompletableFuture() {
        return completableFuture;
    }
}