package net.juicy.api.listener.listeners;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.load.types.ILoadableListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements ILoadableListener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {

        JuicyAPIPlugin.getPlugin().getItemManager().give(event.getPlayer());

    }
}