package net.juicy.api.utils.menu.item.animation;

import lombok.Value;
import net.juicy.api.utils.placeholder.IPlaceholder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Value
public class AnimationLore {

    IPlaceholder placeholder;
    List<String> lore;

    public void display(ItemStack itemStack) {

        List<String> replaced = new ArrayList<>();

        lore.forEach(string -> replaced.add(placeholder.replace(string, null)));

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(replaced);

        itemStack.setItemMeta(itemMeta);

    }
}