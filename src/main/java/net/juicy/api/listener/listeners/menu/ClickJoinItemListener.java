package net.juicy.api.listener.listeners.menu;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.load.types.ILoadableListener;
import net.juicy.api.utils.menu.item.JoinItem;
import net.juicy.player.JuicyPlayerPlugin;
import net.juicy.player.player.auth.AuthPlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickJoinItemListener implements ILoadableListener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getHand() != null)
            if (event.getHand().equals(EquipmentSlot.HAND))
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                    if (event.getItem() != null)
                        if (JuicyPlayerPlugin.getPlugin().getAuthPlayerManager().getPlayer(player).getStatus().equals(AuthPlayerStatus.AUTHORIZED)) {

                            JoinItem item = JuicyAPIPlugin.getPlugin().getItemManager().getItemByItemStack(event.getItem());

                            if (item != null)
                                item.invokeCommand(player);

                        }
    }
}