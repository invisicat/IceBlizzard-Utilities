package dev.blizzardutils.string;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringUtils {

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendFormattedMessage(Player player, String text) {
        player.sendMessage(format(text));
    }

    public static void sendCenteredFormattedMessage(Player player, String message) {
        int CENTER_PX = 154;
        if (message == null || message.equals("")) {
            player.sendMessage("");
            return;
        }

        message = format(message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '&') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder sb = new StringBuilder();

        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        player.sendMessage(sb.toString() + message);
    }
}