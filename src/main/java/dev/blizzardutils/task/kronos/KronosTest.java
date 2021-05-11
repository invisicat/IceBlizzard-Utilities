package dev.blizzardutils.task.kronos;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class KronosTest {


    public void test() {
        KronosChain.KronosChainBuilder.getInstance().createNewChain(CompletableFuture.completedFuture(true)).runSync(
                new KronosRunnables.ConsumerRunnable<>(KronosChain.KronosChainBuilder.getInstance(),
                        (Consumer<Object>) o -> System.out.println("Hi"), true));

    }
}
