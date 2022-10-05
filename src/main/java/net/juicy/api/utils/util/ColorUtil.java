package net.juicy.api.utils.util;

import org.bukkit.Color;
import net.md_5.bungee.api.ChatColor;

import java.util.stream.IntStream;

public class ColorUtil {

    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    public static String makeColor(String text){

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++)
            if (texts[i].equalsIgnoreCase("&")) {

                i++;

                if (texts[i].charAt(0) == '#')
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)) + texts[i].substring(7));
                else
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));

            } else
                finalText.append(texts[i]);

        return finalText.toString();

    }
    
    public static Color chatColorToColor(ChatColor chatColor) {

        if (chatColor.equals(ChatColor.RED))
            return Color.RED;

        if (chatColor.equals(ChatColor.GREEN))
            return Color.LIME;

        if (chatColor.equals(ChatColor.BLUE))
            return Color.fromRGB(0, 128, 255);

        if (chatColor.equals(ChatColor.YELLOW))
            return Color.YELLOW;

        if (chatColor.equals(ChatColor.WHITE))
            return Color.WHITE;

        if (chatColor.equals(ChatColor.BLACK))
            return Color.BLACK;

        if (chatColor.equals(ChatColor.GOLD))
            return Color.ORANGE;

        if (chatColor.equals(ChatColor.AQUA))
            return Color.TEAL;

        if (chatColor.equals(ChatColor.GRAY))
            return Color.SILVER;

        if (chatColor.equals(ChatColor.DARK_GRAY))
            return Color.GRAY;

        if (chatColor.equals(ChatColor.DARK_AQUA))
            return Color.AQUA;

        if (chatColor.equals(ChatColor.DARK_BLUE))
            return Color.BLUE;

        if (chatColor.equals(ChatColor.DARK_GREEN))
            return Color.GREEN;

        if (chatColor.equals(ChatColor.DARK_PURPLE))
            return Color.MAROON;

        if (chatColor.equals(ChatColor.DARK_RED))
            return Color.fromRGB(175, 35, 35);

        if (chatColor.equals(ChatColor.LIGHT_PURPLE))
            return Color.FUCHSIA;

        return null;

    }
    
    public static Color deseralizeColor(String color) {

        if (color.equalsIgnoreCase("GREEN"))
            return Color.GREEN;

        if (color.equalsIgnoreCase("RED"))
            return Color.RED;

        if (color.equalsIgnoreCase("BLUE"))
            return Color.BLUE;

        if (color.equalsIgnoreCase("YELLOW"))
            return Color.YELLOW;

        if (color.equalsIgnoreCase("BLACK"))
            return Color.BLACK;

        if (color.equalsIgnoreCase("AQUA"))
            return Color.AQUA;

        if (color.equalsIgnoreCase("FUCHSIA"))
            return Color.FUCHSIA;

        if (color.equalsIgnoreCase("WHITE"))
            return Color.WHITE;

        if (color.equalsIgnoreCase("TEAL"))
            return Color.TEAL;

        if (color.equalsIgnoreCase("SILVER"))
            return Color.SILVER;

        if (color.equalsIgnoreCase("PURPLE"))
            return Color.PURPLE;

        if (color.equalsIgnoreCase("ORANGE"))
            return Color.ORANGE;

        if (color.equalsIgnoreCase("OLIVE"))
            return Color.OLIVE;

        if (color.equalsIgnoreCase("NAVY"))
            return Color.NAVY;

        if (color.equalsIgnoreCase("MAROON"))
            return Color.MAROON;

        if (color.equalsIgnoreCase("LIME"))
            return Color.LIME;

        if (color.equalsIgnoreCase("GRAY"))
            return Color.GRAY;

        return null;

    }
    
    public static Color dyeIDtoColor(int id) {

        if (id == 0)
            return Color.BLACK;

        if (id == 1)
            return Color.RED;

        if (id == 2)
            return Color.GREEN;

        if (id == 3)
            return Color.OLIVE;

        if (id == 4)
            return Color.BLUE;

        if (id == 5)
            return Color.MAROON;

        if (id == 6)
            return Color.AQUA;

        if (id == 7)
            return Color.SILVER;

        if (id == 8)
            return Color.GRAY;

        if (id == 9)
            return Color.FUCHSIA;

        if (id == 10)
            return Color.LIME;

        if (id == 11)
            return Color.YELLOW;

        if (id == 12)
            return Color.NAVY;

        if (id == 13)
            return Color.TEAL;

        if (id == 14)
            return Color.ORANGE;

        if (id == 15)
            return Color.WHITE;

        return null;

    }
    
    public static Color woolIDtoColor(int id) {

        if (id == 0)
            return Color.WHITE;

        if (id == 1)
            return Color.ORANGE;

        if (id == 2)
            return Color.NAVY;

        if (id == 3)
            return Color.TEAL;

        if (id == 4)
            return Color.YELLOW;

        if (id == 5)
            return Color.LIME;

        if (id == 6)
            return Color.FUCHSIA;

        if (id == 7)
            return Color.GRAY;

        if (id == 8)
            return Color.SILVER;

        if (id == 9)
            return Color.AQUA;

        if (id == 10)
            return Color.MAROON;

        if (id == 11)
            return Color.BLUE;

        if (id == 12)
            return Color.OLIVE;

        if (id == 13)
            return Color.GREEN;

        if (id == 14)
            return Color.RED;

        if (id == 15)
            return Color.BLACK;

        return null;

    }
}
