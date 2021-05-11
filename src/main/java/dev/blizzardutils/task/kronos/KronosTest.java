package dev.blizzardutils.task.kronos;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class KronosTest {


    public void test() {
        KronosChain.KronosChainBuilder init = KronosChain.KronosChainBuilder.getInstance().init(CompletableFuture.completedFuture(true));
        init.runSync(new KronosRunnables.ConsumerRunnable<>(init, (Consumer<Object>) o -> {

        }, 2));

    }
}
