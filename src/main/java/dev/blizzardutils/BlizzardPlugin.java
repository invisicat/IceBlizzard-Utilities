package dev.blizzardutils;

import org.bukkit.plugin.java.JavaPlugin;

public class BlizzardPlugin {

    private static JavaPlugin plugin = null;

    public static JavaPlugin getPlugin() {
        if (BlizzardPlugin.plugin == null) {
            BlizzardPlugin.plugin = JavaPlugin.getProvidingPlugin(BlizzardPlugin.class);
        }

        return plugin;
    }
}
