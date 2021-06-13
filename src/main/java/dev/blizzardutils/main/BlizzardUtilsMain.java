package dev.blizzardutils.main;

import dev.blizzardutils.kronos.chain.KronosRunMethods;
import org.bukkit.plugin.java.JavaPlugin;


public class BlizzardUtilsMain extends JavaPlugin {

    private KronosRunMethods kronosRunMethods;

    public void onEnable() {
        kronosRunMethods = new KronosRunMethods(this);
    }

    public void onDisable() {

    }

    public static BlizzardUtilsMain getInstance() {
        return BlizzardUtilsMain.getPlugin(BlizzardUtilsMain.class);
    }


    public KronosRunMethods getKronosRunMethods() {
        return kronosRunMethods;
    }
}