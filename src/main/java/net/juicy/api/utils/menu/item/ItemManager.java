package net.juicy.api.utils.menu.item;

import lombok.Value;
import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.JuicyItem;
import net.juicy.api.utils.load.ILoadable;
import net.juicy.api.utils.util.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Value
public class ItemManager implements ILoadable {

    JuicyAPIPlugin plugin = JuicyAPIPlugin.getPlugin();

    List<JoinItem> joinItems = new ArrayList<>();

    public ItemManager() {

        plugin.getLoader().load(this);

    }

    public JoinItem getItemByItemStack(ItemStack itemStack) {

        for (JoinItem joinItem : joinItems)
            if (joinItem.getJuicyItem().getItemStack().equals(itemStack))
                return joinItem;

        return null;

    }

    public void give(Player player) {

        getJoinItems()
                .stream()
                .filter(joinItem -> !player.getInventory().contains(joinItem.getJuicyItem().getItemStack()))
                .forEach(joinItem -> joinItem.give(player));

    }

    public void load() {

        FileConfiguration config = plugin.getConfig();

        config.getConfigurationSection("joinItems")
                .getKeys(false)
                .forEach(item -> {

                    ConfigurationSection itemSection = config.getConfigurationSection("joinItems." + item);

                    if (itemSection != null)
                        joinItems.add(new JoinItem(new JuicyItem(itemSection), itemSection.getString("command")));

                });
    }
}