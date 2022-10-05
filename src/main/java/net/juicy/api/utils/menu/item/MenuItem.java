package net.juicy.api.utils.menu.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.server.JuicyServer;
import net.juicy.api.server.JuicyServerManager;
import net.juicy.api.utils.JuicyItem;
import net.juicy.api.utils.Utils;
import net.juicy.api.utils.util.collection.ArrayManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
public class MenuItem {

    private final JuicyItem juicyItem;

    private final String command;

    public void display(Inventory inventory) {

        inventory.setItem(juicyItem.getSlot(), juicyItem.getItemStack());

    }

    public void invokeCommand(Player player) {

        if (command.startsWith("server")) {

            JuicyServerManager juicyServerManager = JuicyAPIPlugin.getPlugin().getServerManager();
            AtomicReference<JuicyServer> toConnect = null;

            if (command.contains(", ")) {

                String[] servers = command.split(", ");
                Arrays.stream(servers).forEach(serverName -> serverName.replace("server:", ""));

                Arrays.stream(servers).filter(juicyServerManager::serverExists)
                        .forEach(serverName -> {

                            JuicyServer juicyServer = juicyServerManager.getServer(serverName);

                            if (toConnect.get() == null)
                                toConnect.set(juicyServer);
                            else if (juicyServerManager.tryToConnect(player, juicyServer))
                                toConnect.set(juicyServer);

                        });
            }

            if (toConnect.get() != null) {

                toConnect.set(juicyServerManager.getServer(String.join("", new ArrayManager<>(command.split(" ")).removeElement(0))));
                Utils.connectPlayerToServer(player, toConnect.get().getName());

            } else
                player.sendMessage(JuicyAPIPlugin.getPlugin().replace("%prefix%Вы не можете подключиться к серверу!"));

        } else if (command.startsWith("menu")) {

            player.closeInventory();
            JuicyAPIPlugin.getPlugin().getGuiManager().openGUI(player, String.join("", new ArrayManager<>(command.split(" ")).removeElement(0)));

        } else
            Bukkit.dispatchCommand(player, command);

    }
}