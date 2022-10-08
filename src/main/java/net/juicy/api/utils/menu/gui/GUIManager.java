package net.juicy.api.utils.menu.gui;

import lombok.Value;
import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.JuicyItem;
import net.juicy.api.utils.load.ILoadable;
import net.juicy.api.utils.menu.item.animation.AnimatedItem;
import net.juicy.api.utils.menu.item.animation.AnimationLore;
import net.juicy.api.utils.placeholder.placeholders.ServerPlaceholder;
import net.juicy.api.utils.util.collection.ArrayManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.stream.IntStream;

@Value
public class GUIManager implements ILoadable {

    JuicyAPIPlugin plugin = JuicyAPIPlugin.getPlugin();

    Map<String, GUI> guis = new HashMap<>();
    Map<Player, GUI> openedGUIs = new HashMap<>();

    public GUIManager() {

        plugin.getLoader().load(this);

    }

    public GUI getGUI(String name) {

        return guis.get(name);

    }

    public void openGUI(Player player, String name) {

        GUI gui = getGUI(name);

        if (gui != null) {

            gui.open(player);
            openedGUIs.put(player, gui);

        }
    }

    public GUI getOpenedByPlayer(Player player) {

        return openedGUIs.get(player);

    }

    public void load() {

        FileConfiguration config = plugin.getConfig();

        config.getConfigurationSection("gui")
                .getKeys(false)
                .forEach(name -> {

                    ConfigurationSection guiSection = config.getConfigurationSection("gui." + name);

                    Inventory inventory = Bukkit.createInventory(null, guiSection.getInt("slots"),
                            plugin.replace(guiSection.getString("title")));
                    List<AnimatedItem> items;

                    if (guiSection.contains("items")) {

                        items = new ArrayList<>();

                        guiSection.getConfigurationSection("items")
                                .getKeys(false)
                                .forEach(item -> {

                                    ConfigurationSection itemSection = guiSection.getConfigurationSection("items." + item);

                                    ServerPlaceholder placeholder = new ServerPlaceholder(JuicyAPIPlugin.getPlugin().getServerManager().getServer(Bukkit.getMotd()));

                                    String command = itemSection.getString("command");

                                    Queue<String> animationNames = new LinkedList<>();

                                    if (itemSection.contains("names"))
                                        itemSection.getStringList("names")
                                                .forEach(itemName -> animationNames.add(placeholder.replace(itemName, null)));

                                    Queue<AnimationLore> animationLores = new LinkedList<>();

                                    if (itemSection.contains("lores")) {

                                        ConfigurationSection loresSection = itemSection.getConfigurationSection("lores");

                                        IntStream.rangeClosed(1, loresSection.getKeys(false).size())
                                                .forEach(integer -> animationLores.add(new AnimationLore(
                                                        new ServerPlaceholder(JuicyAPIPlugin.getPlugin().getServerManager().getServer(
                                                                command.startsWith("server") ?
                                                                        String.join(" ", new ArrayManager<>(command.split(" "))
                                                                                .removeElement(0)) : Bukkit.getMotd())),
                                                        new ArrayList<>(loresSection.getStringList(String.valueOf(integer))))));

                                    }

                                    items.add(new AnimatedItem(inventory, new JuicyItem(itemSection), command, animationNames, animationLores, 20));

                                });
                    } else
                        items = Collections.emptyList();

                    guis.put(name, new GUI(inventory, items));

                });
    }
}