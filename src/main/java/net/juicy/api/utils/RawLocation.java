package net.juicy.api.utils;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Value
@AllArgsConstructor
public class RawLocation {

    String worldName;

    double x;
    double y;
    double z;

    float yaw;
    float pitch;

    public RawLocation(Location location) {

        this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

    }

    public RawLocation(String worldName, double x, double y, double z) {

        this.worldName = worldName;

        this.x = x;
        this.y = y;
        this.z = z;

        yaw = 0;
        pitch = 0;

    }

    public Location toLocation() {

        return toLocation(Bukkit.getWorld(worldName));

    }

    public Location toLocation(World world) {

        return new Location(world, x, y, z, yaw, pitch);

    }
}