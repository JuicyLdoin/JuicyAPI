package net.juicy.api.utils;

import net.juicy.api.JuicyAPIPlugin;

import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;
import java.util.stream.IntStream;

import com.mysql.cj.exceptions.NumberOutOfRange;

public class Utils {

    public static Integer slotCounter(Integer itemsCount) {

        while (itemsCount % 9 != 0)
            itemsCount++;

        return itemsCount;
    }
    
    public static float getPercentage(int number, float percentage) {

        return number / 100.0f * percentage;

    }
    
    public static String generateRandomID(int length) {

        if (length <= 0)
            throw new NumberOutOfRange("Length cannot be less than 1");

        String uuid = UUID.randomUUID().toString().replace("-", "");

        if (uuid.length() > length)
            throw new ArrayIndexOutOfBoundsException("Length of ID cannot be longer than " + uuid.length());

        String[] rawID = new String[length];

        IntStream.range(0, length)
                .forEach(i -> rawID[i] = uuid.split("")[ThreadLocalRandom.current().nextInt(uuid.length())]);

        return String.join("", rawID);

    }
    
    public static boolean connectPlayerToServer(Player player, String where) {

        try {

            if (where.length() == 0) {

                Bukkit.getConsoleSender().sendMessage("Server was \"\" (empty string) cannot connect to it.");
                return false;

            }

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);

            out.writeUTF("Connect");
            out.writeUTF(where);

            player.sendPluginMessage(JuicyAPIPlugin.getPlugin(), "BungeeCord", byteArray.toByteArray());

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();
            return false;

        }
    }
}