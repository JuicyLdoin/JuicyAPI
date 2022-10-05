package net.juicy.api.utils.util.builders;

import lombok.AllArgsConstructor;
import net.juicy.api.utils.JuicyItem;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class InventoryBuilder implements IBuilder<Inventory> {

    private final Inventory inventory;

    public InventoryBuilder buildItems(List<JuicyItem> items) {

        if (items.isEmpty())
            return this;

        items.stream()
                .filter(item -> inventory.getSize() > item.getSlot())
                .forEach(item -> item.display(inventory));

        return this;

    }

    public InventoryBuilder buildItems(Map<Integer, ItemStack> items) {

        if (items.isEmpty())
            return this;

        items.keySet()
                .stream()
                .filter(slot -> inventory.getSize() - 1 >= slot)
                .forEach(slot -> inventory.setItem(slot, items.get(slot)));

        return this;

    }

    public Inventory build() {

        return inventory;

    }
}