package dev.blizzardutils.task.kronos;

import dev.blizzardutils.BlizzardPlugin;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class KronosChain {


    public static class KronosChainBuilder<K> {

        private CompletableFuture<K> completableFuture = null;
        private AtomicBoolean sync = new AtomicBoolean(false);
        private AtomicBoolean async = new AtomicBoolean(false);
        private AtomicBoolean hasStopped = new AtomicBoolean(false);

        public static KronosChainBuilder getInstance() {
            return new KronosChainBuilder<>();
        }

        public KronosChainBuilder<K> init(CompletableFuture<K> completableFuture) {
            this.completableFuture = completableFuture;
            sync.set(false);
            async.set(false);
            hasStopped.set(false);
            return this;
        }


        public KronosChainBuilder<K> runSync(Runnable runnable) {
            sync.set(true);
            if (sync.get()) {
                Bukkit.getScheduler().runTask(BlizzardPlugin.getPlugin(), runnable);
            }

            sync.set(false);

            if (!sync.get()) hasStopped.set(true);

            return this;
        }

        public KronosChainBuilder<K> runAsync(Runnable runnable) {
            async.set(true);
            if (async.get()) {
                Bukkit.getScheduler().runTaskAsynchronously(BlizzardPlugin.getPlugin(), runnable);
            }

            async.set(false);

            if (!async.get()) hasStopped.set(true);


            return this;
        }

        public KronosChainBuilder<K> runSyncDelayed(Runnable runnable, long l1) {
            sync.set(true);
            if (sync.get()) {
                Bukkit.getScheduler().runTaskLater(BlizzardPlugin.getPlugin(), runnable, l1);
            }

            sync.set(false);

            if (!sync.get()) hasStopped.set(true);


            return this;
        }

        public KronosChainBuilder<K> runAsyncDelayed(Runnable runnable, long l1) {
            async.set(true);
            if (async.get()) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(BlizzardPlugin.getPlugin(), runnable, l1);
            }

            async.set(false);

            if (!async.get()) hasStopped.set(true);

            return this;
        }

        public KronosChainBuilder<K> createNewChain(K value) {
            return init(CompletableFuture.completedFuture(value));
        }


        public AtomicBoolean getSync() {
            return sync;
        }

        public AtomicBoolean getAsync() {
            return async;
        }

        public AtomicBoolean getHasStopped() {
            return hasStopped;
        }
    }
}