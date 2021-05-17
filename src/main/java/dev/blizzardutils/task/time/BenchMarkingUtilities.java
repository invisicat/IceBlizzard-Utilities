package dev.blizzardutils.task.time;

import dev.blizzardutils.string.StringUtils;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class BenchMarkingUtilities {

    public static long initStartTime() {
        return System.nanoTime();
    }

    public static void getFinalResults(Player player) {
        long elapsedTime = System.nanoTime() - initStartTime();
        double seconds = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        double secondDecimalPrecision = (double) elapsedTime / 1000000000.0;
        double ms = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        double minutes = TimeUnit.MINUTES.convert(elapsedTime, TimeUnit.NANOSECONDS);
        player.sendMessage(StringUtils.format("&7[&bBlizzard Utilities Benchmark Results&7]"));
        player.sendMessage(StringUtils.format("&bNanoseconds&7: &f" + elapsedTime + "&bms"));
        player.sendMessage(StringUtils.format("&bMilliseconds&7: &f" + ms + "&bms"));
        player.sendMessage(StringUtils.format("&bSeconds&7: &f" + seconds + "&bms"));
        player.sendMessage(StringUtils.format("&bSecond Precision&7: &f" + secondDecimalPrecision + "&bms"));
        player.sendMessage(StringUtils.format("&bMinutes&7: &f" + minutes + " &bms"));
    }
}
