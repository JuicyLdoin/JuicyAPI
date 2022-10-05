package net.juicy.api.utils;

import lombok.Value;
import net.juicy.api.utils.util.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Value
public class JuicyItem {

    ItemStack itemStack;
    int slot;

    public JuicyItem(ConfigurationSection section) {

        itemStack = ItemUtil.buildItem(section);

        if (section.contains("slot"))
            slot = section.getInt("slot") - 1;
        else
            slot = -1;

    }

    public void display(Inventory inventory) {

        if (slot > -1)
            inventory.setItem(slot, itemStack);

    }
}