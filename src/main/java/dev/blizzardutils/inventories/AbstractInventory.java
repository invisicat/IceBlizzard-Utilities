package dev.blizzardutils.inventories;

import org.bukkit.entity.Player;

public interface AbstractInventory {

    void create();

    void addItems();

    void open(Player player);

    default void register(Player player) {
        create();
        addItems();
        open(player);
    }
}