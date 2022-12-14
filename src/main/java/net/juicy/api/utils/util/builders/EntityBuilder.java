package net.juicy.api.utils.util.builders;

import lombok.AllArgsConstructor;
import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class EntityBuilder implements IBuilder<Entity> {

    private final LivingEntity entity;
    private final List<Integer> tasks = new ArrayList<>();

    public EntityBuilder(Location location, EntityType entityType) {

        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        new BukkitRunnable() {

            public void run() {

                if (entity.isDead()) {

                    tasks.forEach(taskId -> Bukkit.getScheduler().cancelTask(taskId));
                    cancel();

                }
            }
        }.runTaskTimer(JuicyAPIPlugin.getPlugin(), 0, 1);
    }

    public EntityBuilder setHealth(float health) {

        entity.setMaxHealth(health);
        entity.setHealth(health);

        return this;

    }

    public EntityBuilder setDisplayName(String displayName) {

        entity.setCustomName(displayName.replace("&", "§"));
        entity.setCustomNameVisible(true);

        return this;

    }

    public EntityBuilder setItem(EquipmentSlot slot, ItemStack itemStack) {

        entity.getEquipment().setItem(slot, itemStack);
        return this;

    }

    public EntityBuilder setVisible(boolean visible) {

        entity.setInvisible(!visible);
        return this;

    }

    public EntityBuilder setPassenger(Entity entity) {

        this.entity.setPassenger(entity);
        return this;

    }

    public EntityBuilder addBukkitTask(BukkitTask bukkitTask) {

        tasks.add(bukkitTask.getTaskId());
        return this;

    }

    public Entity build() {

        return entity;

    }
}