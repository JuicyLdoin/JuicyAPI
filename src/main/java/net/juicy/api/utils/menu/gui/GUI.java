package net.juicy.api.utils.menu.gui;

import lombok.Value;
import net.juicy.api.utils.menu.item.animation.AnimatedItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

@Value
public class GUI {

    Inventory inventory;
    List<AnimatedItem> items;

    public GUI(Inventory inventory, List<AnimatedItem> items) {

        this.inventory = inventory;
        this.items = items;

        items.forEach(item -> item.display(inventory));

    }

    public Optional<AnimatedItem> getItemByItemStack(ItemStack itemStack) {

        AnimatedItem animatedItem = null;

        for (AnimatedItem animatedItems : items)
            if (animatedItems.getJuicyItem().getItemStack().equals(itemStack)) {

                animatedItem = animatedItems;
                break;

            }

        return Optional.ofNullable(animatedItem);

    }

    public void open(Player player) {

        player.openInventory(inventory);

    }
}