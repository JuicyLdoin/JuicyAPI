package net.juicy.api.utils.menu.item;

import net.juicy.api.utils.JuicyItem;
import org.bukkit.entity.Player;

public class JoinItem extends MenuItem {

    public JoinItem(JuicyItem juicyItem, String command) {

        super(juicyItem, command);

    }

    public void give(Player player) {

        if (getJuicyItem().getSlot() > 8)
            return;

        display(player.getInventory());

    }
}