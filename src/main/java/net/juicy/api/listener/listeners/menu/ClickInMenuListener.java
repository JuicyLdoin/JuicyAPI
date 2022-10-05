package net.juicy.api.listener.listeners.menu;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.load.types.ILoadableListener;
import net.juicy.api.utils.menu.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickInMenuListener implements ILoadableListener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getCurrentItem() != null) {

            ItemStack itemStack = event.getCurrentItem();

            Player player = (Player) event.getWhoClicked();
            GUI gui = JuicyAPIPlugin.getPlugin().getGuiManager().getOpenedByPlayer(player);

            if (gui != null)
                if (gui.getInventory().equals(event.getClickedInventory())) {

                    gui.getItemByItemStack(itemStack).invokeCommand(player);

                    event.setCancelled(true);

                }
        }
    }
}