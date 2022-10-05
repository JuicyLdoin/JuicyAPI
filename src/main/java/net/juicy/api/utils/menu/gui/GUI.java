package net.juicy.api.utils.menu.gui;

import lombok.Value;
import net.juicy.api.utils.menu.item.animation.AnimatedItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Value
public class GUI {

    Inventory inventory;
    List<AnimatedItem> items;

    public GUI(Inventory inventory, List<AnimatedItem> items) {

        this.inventory = inventory;
        this.items = items;

        items.forEach(item -> item.display(inventory));

    }

    public AnimatedItem getItemByItemStack(ItemStack itemStack) {

        for (AnimatedItem animatedItem : items)
            if (animatedItem.getJuicyItem().getItemStack().equals(itemStack))
                return animatedItem;

        return null;

    }

    public void open(Player player) {

        player.openInventory(inventory);

    }
}