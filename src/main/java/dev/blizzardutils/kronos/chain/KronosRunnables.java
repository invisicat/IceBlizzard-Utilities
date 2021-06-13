package dev.blizzardutils.kronos.chain;

import dev.blizzardutils.kronos.util.ThreadContext;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class KronosRunnables {

    public static abstract class AbstractRunnable<K> implements Runnable {

        private KronosChain<? super K> kronosChain;

        private AbstractRunnable(KronosChain<? super K> kronosChain) {
            this.kronosChain = kronosChain;
        }

        public abstract K getCompleteValue();

        void onComplete() {

        }

        @Override
        public void run() {
            if (!kronosChain.getIsCancelled().get()) {
                try {
                    onComplete();
                    kronosChain.complete(getCompleteValue());
                } catch (Throwable throwable) {
                    kronosChain.completeExceptionally(throwable);
                }
            }
        }
    }


    public static class WrappedRunnable<K> extends AbstractRunnable<K> {

        private final Runnable runnable;

        public WrappedRunnable(KronosChain<K> kronosChain, Runnable runnable) {
            super(kronosChain);
            this.runnable = runnable;
        }

        @Override
        void onComplete() {
            super.onComplete();
            this.runnable.run();
        }

        @Override
        public K getCompleteValue() {
            return null;
        }
    }

    public static class FunctionRunnable<C, K> extends AbstractRunnable<C> {

        private final Function<? super K, ? extends C> function;
        private final K value;

        public FunctionRunnable(KronosChain<C> kronosChain, Function<? super K, ? extends C> function, K value) {
            super(kronosChain);
            this.function = function;
            this.value = value;
        }

        @Override
        public C getCompleteValue() {
            return this.function.apply(value);
        }
    }

    public static class ConsumerRunnable<C, K> extends AbstractRunnable<C> {

        private final Consumer<K> consumer;
        private final K value;

        public ConsumerRunnable(KronosChain<C> kronosChain, Consumer<K> consumer, K value) {
            super(kronosChain);
            this.consumer = consumer;
            this.value = value;
        }

        @Override
        public C getCompleteValue() {
            return null;
        }

        @Override
        void onComplete() {
            super.onComplete();
            this.consumer.accept(value);
        }
    }

    public static class SupplierRunnable<K> extends AbstractRunnable<K> {

        private final Supplier<K> supplier;

        public SupplierRunnable(KronosChain<K> kronosChain, Supplier<K> supplier) {
            super(kronosChain);
            this.supplier = supplier;
        }


        @Override
        public K getCompleteValue() {
            return this.supplier.get();
        }
    }

    public static class ComposeRunnable<C, K> implements Runnable {

        private final KronosChain<C> kronosChain;
        private final Function<? super K, ? extends KronosChain<C>> function;
        private final K value;
        private final ThreadContext threadContext;


       public ComposeRunnable(KronosChain<C> kronosChain, Function<? super K, ? extends KronosChain<C>> function, K value, ThreadContext threadContext) {
            this.kronosChain = kronosChain;
            this.function = function;
            this.value = value;
            this.threadContext = threadContext;
        }

        @Override
        public void run() {
            if (!kronosChain.getIsCancelled().get()) {
                try {
                    KronosChain<C> applied = function.apply(value);
                    if (applied != null) {
                        applied.accept(kronosChain::complete, threadContext, 0);
                    } else {
                        kronosChain.complete(null);
                    }
                } catch (Throwable throwable) {
                    kronosChain.completeExceptionally(throwable);
                }
            }
        }
    }
}

