package dev.blizzardutils.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.blizzardutils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullBuilder {

    public static class Builder {

        private static Builder instance = null;
        private ItemStack item;
        private SkullMeta meta;

        public static synchronized Builder getInstance() {
            if (instance == null) instance = new Builder();
            return instance;
        }

        public Builder createSkull() {
            item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            meta = (SkullMeta) item.getItemMeta();
            return this;
        }

        public Builder setOwner(String name) {
            meta.setOwner(name);
            return this;
        }

        public Builder setName(String name) {
            meta.setDisplayName(StringUtils.format(name));
            return this;
        }

        public Builder setMeta() {
            item.setItemMeta(meta);
            return this;
        }

        public ItemStack toItemStack() {
            return item;
        }


        public Builder getItem(String skullOwner) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), (String) null);
            PropertyMap propertyMap = profile.getProperties();
            if (propertyMap == null) {
                throw new IllegalStateException("Profile doesn't contain a property map");
            } else {
                propertyMap.put("textures", new Property("textures", skullOwner));
                Class metaclass = meta.getClass();

                try {
                    getField(metaclass, "profile", GameProfile.class, 0).set(meta, profile);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }


        private static <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index) {
            Field[] fields = target.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                    field.setAccessible(true);
                    return field;
                }
            }

            if (target.getSuperclass() != null) {
                return getField(target.getSuperclass(), name, fieldType, index);
            } else {
                throw new IllegalArgumentException("Cannot find field with type " + fieldType);
            }
        }
    }
}
