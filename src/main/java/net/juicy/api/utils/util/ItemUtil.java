package net.juicy.api.utils.util;

import java.lang.reflect.Field;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

import net.juicy.api.utils.placeholder.IPlaceholder;
import net.juicy.api.utils.placeholder.placeholders.APIPlaceholder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.Arrays;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.ItemFlag;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtil {

    public static ItemStack buildItem(ConfigurationSection section, IPlaceholder placeholder, Player player) {

        Material material = Material.matchMaterial(Objects.requireNonNull(section.getString("material")));
        int amount = section.contains("amount") ? section.getInt("amount") : 1;

        assert material != null;

        ItemStack itemStack = new ItemStack(material, amount);

        if (section.contains("enchantments"))
            section.getStringList("enchantments").forEach(ench -> itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(ench.split("-")[0])), Integer.parseInt(ench.split("-")[1])));

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (section.contains("name"))
            itemMeta.setDisplayName(placeholder.replace(section.getString("name"), player));

        if (section.contains("lore")) {

            List<String> lore = new ArrayList<>();

            section.getStringList("lore").forEach(lorePiece -> lore.add(placeholder.replace(lorePiece, player)));

            itemMeta.setLore(lore);

        }

        if (section.contains("flags"))
            section.getStringList("flags").forEach(flag -> itemMeta.addItemFlags(ItemFlag.valueOf(flag)));

        if (section.contains("options")) {

            ConfigurationSection options = section.getConfigurationSection("options");

            if (options.contains("texture") && material.equals(Material.PLAYER_HEAD))
                setHead(itemStack, options.getString("texture"));

            if (options.contains("unbreakable"))
                itemMeta.setUnbreakable(options.getBoolean("unbreakable"));

            if (options.contains("color"))
                try {

                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                    String color = options.getString("color");
                    leatherArmorMeta.setColor(Color.fromRGB(Integer.parseInt(color.split(" ")[0]), Integer.parseInt(color.split(" ")[1]), Integer.parseInt(color.split(" ")[2])));

                } catch (Exception ignored) {}

            if (options.contains("potion"))
                try {

                    ConfigurationSection potionSection = options.getConfigurationSection("potion");
                    PotionMeta potionMeta = (PotionMeta) itemMeta;

                    if (potionSection.contains("color"))
                        potionMeta.setColor(ColorUtil.deserializeColor(potionSection.getString("color")));

                    for (String potion : potionSection.getStringList("potion")) {

                        String[] potionData = potion.split("=");
                        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(potionData[0]),
                                Integer.parseInt(potionData[2]), Integer.parseInt(potionData[1]) - 1), true);

                    }
                } catch (Exception ignored) {}
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }

    public static ItemStack buildItem(ConfigurationSection section) {

        return buildItem(section, APIPlaceholder.getApiPlaceholder(), null);

    }
    
    public static ItemStack getItem(Material material, Integer amount, String name, List<String> lore) {

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);

        if (lore != null)
            itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }
    
    public static ItemStack getItem(Material material, Integer amount, String name, String... lore) {

        return getItem(material, amount, name, Arrays.asList(lore));

    }
    
    public static ItemStack getItem(ItemStack itemstack, String enchantment) {

        String[] totalEnch = enchantment.split(":");
        Enchantment aEnch = Enchantment.getByName(totalEnch[0]);

        int aEnchLvl = 1;

        try {

            aEnchLvl = Integer.parseInt(totalEnch[1]);

        } catch (NumberFormatException ignored) {}

        itemstack.addEnchantment(aEnch, aEnchLvl);
        return itemstack;

    }
    
    public static ItemStack setHead(ItemStack item, String texture) {

        SkullMeta headMeta = (SkullMeta)item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", texture));

        try {

            Field profileField = headMeta.getClass().getDeclaredField("profile");

            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        item.setItemMeta(headMeta);
        return item;

    }
}