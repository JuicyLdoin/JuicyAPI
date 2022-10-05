package net.juicy.api.utils.menu.item.animation;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.JuicyItem;
import net.juicy.api.utils.menu.item.MenuItem;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;

public class AnimatedItem extends MenuItem {

    private final Queue<String> nameQueue;
    private final Queue<AnimationLore> loreQueue;

    public AnimatedItem(Inventory inventory, JuicyItem juicyItem, String command, Queue<String> nameQueue, Queue<AnimationLore> loreQueue, int period) {

        super(juicyItem, command);

        this.nameQueue = nameQueue;
        this.loreQueue = loreQueue;

        new BukkitRunnable() {

            public void run() {

                call(inventory);

            }
        }.runTaskTimer(JuicyAPIPlugin.getPlugin(), 0, period);
    }

    public void call(Inventory inventory) {

        ItemStack itemStack = getJuicyItem().getItemStack();

        AnimationLore lore = loreQueue.poll();
        String name = nameQueue.poll();

        loreQueue.add(lore);
        nameQueue.add(name);

        if (lore != null)
            lore.display(itemStack);

        if (name != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);

            itemStack.setItemMeta(itemMeta);

        }

        display(inventory);

    }
}