package net.juicy.api.utils.util;

import lombok.experimental.UtilityClass;
import net.juicy.api.utils.RawLocation;
import net.juicy.api.utils.util.number.NumberRange;
import net.juicy.api.utils.util.number.NumberUtil;
import org.bukkit.Location;

@UtilityClass
public class LocationUtil {

    public static String getLocation(Location location) {

        return location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ();

    }

    public static RawLocation getRawLocation(String location) {

        String world = null;

        double x = 0;
        double y = 0;
        double z = 0;

        float yaw = 0;
        float pitch = 0;

        String[] _loc = location.split(" ");

        try {

            world = _loc[0];

            x = Double.parseDouble(_loc[1]);
            y = Double.parseDouble(_loc[2]);
            z = Double.parseDouble(_loc[3]);

            try {

                yaw = Float.parseFloat(_loc[4]);
                pitch = Float.parseFloat(_loc[5]);

            } catch (IndexOutOfBoundsException ex2) {

                yaw = 0;
                pitch = 0;

            }
        } catch (NullPointerException ex) {

            ex.printStackTrace();

        }

        return new RawLocation(world, x, y, z, yaw, pitch);

    }
    
    public static Location getLocation(String location) {

        return getRawLocation(location).toLocation();

    }
    
    public static void setMinAndMaxLocations(Location minPoint, Location maxPoint) {

        if (minPoint.getX() > maxPoint.getX()) {

            double x = maxPoint.getX();

            maxPoint.setX(minPoint.getX());
            minPoint.setX(x);

        }

        if (minPoint.getY() > maxPoint.getY()) {

            double y = maxPoint.getY();

            maxPoint.setY(minPoint.getY());
            minPoint.setY(y);

        }

        if (minPoint.getZ() > maxPoint.getZ()) {

            double z = maxPoint.getZ();

            maxPoint.setZ(minPoint.getZ());
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

    public static int[] getAddition(Location center, Location location) {

        return getAddition(center, location.getBlockX(), location.getBlockY(), location.getBlockZ());

    }

    public static Location applyAddition(Location location, float x, float y, float z) {

        return location.add(x, y, z);

    }

    public static Location applyAddition(Location location, float[] xyz) {

        return location.add(xyz[0], xyz[1], xyz[2]);

    }

    public static Location applyAddition(Location location, NumberRange[] xyz) {

        return location.add(NumberUtil.randomInRange(xyz[0]), NumberUtil.randomInRange(xyz[1]), NumberUtil.randomInRange(xyz[2]));

    }
}
