package dev.blizzardutils.task.kronos;

import java.util.function.Consumer;

public class KronosRunnables {

    public static abstract class AbstractRunnable<K> implements Runnable {

        private KronosChain.KronosChainBuilder<? super K> kronosChainBuilder;

        public AbstractRunnable(KronosChain.KronosChainBuilder<? super K> kronosChainBuilder) {
            this.kronosChainBuilder = kronosChainBuilder;
        }

        public abstract K getCompleteValue();

        void onComplete() {

        }

        @Override
        public void run() {
            if (!kronosChainBuilder.getHasStopped().get()) {
                try {
                    onComplete();
                    kronosChainBuilder.createNewChain(getCompleteValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ConsumerRunnable<C, K> extends AbstractRunnable<K> {

        private final Consumer<C> consumer;
        private final C value;

        ConsumerRunnable(KronosChain.KronosChainBuilder<K> kronosChainBuilder, Consumer<C> consumer, C value) {
            super(kronosChainBuilder);
            this.consumer = consumer;
            this.value = value;
        }

        @Override
        public K getCompleteValue() {
            return null;
        }

        @Override
        void onComplete() {
            super.onComplete();
            this.consumer.accept(value);
        }
    }
}

