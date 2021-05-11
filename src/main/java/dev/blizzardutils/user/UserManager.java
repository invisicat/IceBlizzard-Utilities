package dev.blizzardutils.user;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.Map;

public class UserManager {

    private final Map<String, User> userMap = Maps.newConcurrentMap();


    public UserManager() {

    }

    public void defineUser(String playerName, String playerUUID) {
        if (isUserDefined(playerName)) return;
        userMap.put(playerName, new User(playerName, playerUUID));
    }

    public boolean isUserDefined(String playerName) {
        return userMap.containsKey(playerName);
    }

    public void removeUser(String playerName) {
        if (!isUserDefined(playerName)) return;
        userMap.remove(playerName);
    }

    public Collection<User> getUsers() {
        return userMap.values();
    }

    public Player getPlayer(String name) {
        return Bukkit.getPlayer(userMap.get(name).getPlayerName());
    }

    /*

    @Used if this plugin was reloaded through Plugman or something

     */

    public void addAllToUserMapIfAbsent() {
        if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
            return;
        }
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (isUserDefined(all.getName())) return;
            defineUser(all.getName(), all.getUniqueId().toString());
        }
    }

}
