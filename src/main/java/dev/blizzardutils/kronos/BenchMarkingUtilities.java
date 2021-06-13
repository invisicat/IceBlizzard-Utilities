package dev.blizzardutils.kronos;

import dev.blizzardutils.string.StringUtils;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class BenchMarkingUtilities {

    public static void getFinalResults(Player player, long start) {
        long elapsedTime = System.currentTimeMillis() - start;
        double seconds = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.MILLISECONDS);
        double ms = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.MILLISECONDS);
        double minutes = TimeUnit.MINUTES.convert(elapsedTime, TimeUnit.MILLISECONDS);
        player.sendMessage(StringUtils.format("&7[&bBlizzard Utilities Benchmark Results&7]"));
        player.sendMessage(StringUtils.format("&bMilliseconds&7: &f" + ms + "&bms"));
        player.sendMessage(StringUtils.format("&bSeconds&7: &f" + seconds + "&bms"));
        player.sendMessage(StringUtils.format("&bMinutes&7: &f" + minutes + " &bms"));
    }
}
