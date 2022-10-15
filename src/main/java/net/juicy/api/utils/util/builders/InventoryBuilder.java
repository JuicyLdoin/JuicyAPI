package net.juicy.api.utils.util.builders;

import lombok.AllArgsConstructor;
import lombok.NonNull;
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

    public InventoryBuilder addItemToRow(int row, ItemStack itemStack, @NonNull RowType rowType) {

        if (inventory.getSize() / 9 > row)
            throw new ArrayIndexOutOfBoundsException();

        if (rowType.equals(RowType.HORIZONTAL)) {

            int startSlot = 9 * row - 1;
            int endSlot = 9 * (row);

            for (int slot = startSlot; slot < endSlot; slot++)
                if (inventory.getItem(slot) == null) {

                    inventory.setItem(slot, itemStack);
                    break;

                }
        } else if (rowType.equals(RowType.VERTICAL)) {

            int startSlot = row - 1;
            int endSlot = inventory.getSize() - row - 1;

            for (int slot = startSlot; slot < endSlot; slot += 9)
                if (inventory.getItem(slot) == null) {

                    inventory.setItem(slot, itemStack);
                    break;

                }
        }

        return this;

    }

    public InventoryBuilder addItemToRow(int row, ItemStack itemStack) {

        addItemToRow(row, itemStack, RowType.HORIZONTAL);
        return this;

    }

    public Inventory build() {

        return inventory;

    }

    public enum RowType {

        HORIZONTAL,
        VERTICAL

    }
}