package dev.blizzardutils.builder;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.List;

public class FireworkBuilder {

    public static class Builder {

        private static Builder instance = null;
        private Location location;
        private int power;
        private List<Color> mainColors;
        private boolean flicker;
        private boolean trail;
        private List<Color> fadeColors;
        private FireworkEffect.Type type;


        public static synchronized Builder getInstance() {
            if (instance == null) instance = new Builder();
            return instance;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setPower(int power) {
            this.power = power;
            return this;
        }

        public Builder setMainColors(List<Color> mainColors) {
            this.mainColors = mainColors;
            return this;
        }

        public Builder setFlicker(boolean flicker) {
            this.flicker = flicker;
            return this;
        }

        public Builder setTrail(boolean trail) {
            this.trail = trail;
            return this;
        }

        public Builder setFadeColors(List<Color> fadeColors) {
            this.fadeColors = fadeColors;
            return this;
        }

        public Builder setType(FireworkEffect.Type type){
            this.type = type;
            return this;
        }

        public Firework build() {
            Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            meta.setPower(power);
            meta.addEffects(FireworkEffect.builder().withColor(mainColors).flicker(flicker).trail(trail).withFade(fadeColors)
                    .with(type).build());
            firework.setFireworkMeta(meta);
            return firework;
        }
    }
}
