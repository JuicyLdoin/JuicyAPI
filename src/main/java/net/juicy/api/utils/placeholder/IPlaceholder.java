package net.juicy.api.utils.placeholder;

import org.bukkit.entity.Player;

public interface IPlaceholder {

    String replace(String string, Player player);

}