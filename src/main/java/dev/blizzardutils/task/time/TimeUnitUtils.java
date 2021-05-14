package dev.blizzardutils.task.time;

public class TimeUnitUtils {

    public static long milliToSecond(long value) {
        return value / 1000;
    }

    public static long secondToMilli(long value) {
        return value * 1000;
    }

    public static long secondToMinute(long value) {
        return value / 60;
    }

    public static int ticksToSeconds(int seconds) {
        return 20 * seconds;
    }

    public static int secondsToTicks(int seconds) {
        return seconds / 20;
    }
}
