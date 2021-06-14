package dev.blizzardutils.builder;

import dev.blizzardutils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemBuilder {

    public static class Builder {

        private static Builder instance = null;

        private Material material;

        private int amount;

        private byte id;

        private String name;

        private List<String> lore;

        private Map<Enchantment, Integer> enchantmentMap;

        public static synchronized Builder getInstance() {

            if (instance == null) instance = new Builder();
            return instance;
        }

        public Builder itemType(Material material) {
            this.material = material;
            return this;
        }

        public Builder itemAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder itemID(byte id) {
            this.id = id;
            return this;
        }

        public Builder itemName(String name) {
            this.name = name;
            return this;
        }

        public Builder itemLore(List<String> lores) {
            lore = lores.stream().map(StringUtils::format).collect(Collectors.toList());
            return this;
        }

        public Builder itemEnchant(Map<Enchantment, Integer> enchants) {
            this.enchantmentMap = enchants;
            return this;
        }

        public ItemStack build() {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(StringUtils.format(name));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

        public ItemStack buildWithID() {
            ItemStack item = new ItemStack(material, amount, id);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(StringUtils.format(name));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

        public ItemStack buildWithEnchants() {
            ItemStack item = new ItemStack(material, amount);
            item.addUnsafeEnchantments(enchantmentMap);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(StringUtils.format(name));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

        public ItemStack buildWithIDAndEnchants() {
            ItemStack item = new ItemStack(material, amount, id);
            item.addUnsafeEnchantments(enchantmentMap);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(StringUtils.format(name));
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

        /*

        @Utility method used to check if item is null or not. Good for interact or inventory listeners.

         */
        public static boolean hasDisplayName(ItemStack currentitem) {
            if (currentitem == null) {
                return false;
            }
            if (!currentitem.hasItemMeta()) {
                return false;
            }
            if (!currentitem.getItemMeta().hasDisplayName()) {
                return false;
            }
            return true;
        }
    }
}
