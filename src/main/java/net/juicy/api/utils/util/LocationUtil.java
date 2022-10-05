package net.juicy.api.utils.util;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String getLocation(Location location) {

        return location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ();

    }
    
    public static Location getLocation(String location) {

        World world = null;

        double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        double yaw = 0.0;
        double pitch = 0.0;

        String[] _loc = location.split(" ");

        try {

            world = Bukkit.getWorld(_loc[0]);

            x = Double.parseDouble(_loc[1]);
            y = Double.parseDouble(_loc[2]);
            z = Double.parseDouble(_loc[3]);

            try {

                yaw = Double.parseDouble(_loc[4]);
                pitch = Double.parseDouble(_loc[5]);

            } catch (IndexOutOfBoundsException ex2) {

                yaw = 0.0;
                pitch = 0.0;

            }
        } catch (NullPointerException ex) {

            ex.printStackTrace();

        }

        Location loc = new Location(world, x, y, z, (float)yaw, (float)pitch);
        return (loc.getWorld() != null) ? loc : null;

    }
    
    public static void setMinAndMaxLocations(Location minPoint, Location maxPoint) {

        if (minPoint.getX() > maxPoint.getX()) {

            int x = maxPoint.getBlockX();

            maxPoint.setX(minPoint.getBlockX());
            minPoint.setX(x);

        }

        if (minPoint.getY() > maxPoint.getY()) {

            int y = maxPoint.getBlockY();

            maxPoint.setY(minPoint.getBlockY());
            minPoint.setY(y);

        }

        if (minPoint.getZ() > maxPoint.getZ()) {

            int z = maxPoint.getBlockZ();

            maxPoint.setZ(minPoint.getBlockZ());
            minPoint.setZ(z);

        }
    }

    public static Location setInt(Location location) {

        location.setX(location.getBlockX());
        location.setY(location.getBlockY());
        location.setZ(location.getBlockZ());

        return location;

    }
    
    public static int[] getAddition(Location center, int x, int y, int z) {

        return new int[]{ center.getBlockX() - x, center.getBlockY() - y, center.getBlockZ() - z };

    }
}
