package net.juicy.api.utils.load.types;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.load.ILoadable;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public interface ILoadableListener extends Listener, ILoadable {

    default void load() {

        Bukkit.getPluginManager().registerEvents(this, JuicyAPIPlugin.getPlugin());

    }
}