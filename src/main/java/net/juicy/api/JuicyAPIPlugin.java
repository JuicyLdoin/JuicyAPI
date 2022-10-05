package net.juicy.api;

import lombok.Getter;
import net.juicy.api.listener.ListenerManager;
import net.juicy.api.server.JuicyServerManager;
import net.juicy.api.utils.menu.gui.GUIManager;
import net.juicy.api.utils.menu.item.ItemManager;
import org.bukkit.Bukkit;
import net.juicy.api.utils.load.Loader;
import net.juicy.api.utils.log.JuicyLogger;

@Getter
public class JuicyAPIPlugin extends JuicyPlugin {

    private static JuicyAPIPlugin plugin;

    public static JuicyAPIPlugin getPlugin() {

        return JuicyAPIPlugin.plugin;

    }

    private JuicyServerManager serverManager;
    private JuicyLogger juicyLogger;
    private Loader loader;

    private ItemManager itemManager;
    private GUIManager guiManager;

    public void onEnable() {

        JuicyAPIPlugin.plugin = this;

        saveDefaultConfig();

        juicyLogger = new JuicyLogger(this);
        loader = new Loader();

        loader.load(new ListenerManager());

        serverManager = new JuicyServerManager();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        loader.loadAll();

        itemManager = new ItemManager();
        guiManager = new GUIManager();

        Bukkit.getConsoleSender().sendMessage("JuicyAPI Loaded!");

    }
    
    public void onDisable() {

        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        loader.unLoadAll();

    }

    public String replace(String string) {

        if (string == null || string.equals(""))
            return "";

        string = string
                .replace("%totalOnline%", String.valueOf(serverManager.getTotalOnline()))
                .replace("%maxTotalOnline%", String.valueOf(serverManager.getMaxOnline()));

        return super.replace(string);

    }

    public String getPrefix() {

        return "§e§lJuicyGalaxy §f>> ";

    }

    public void reload() {

        reloadConfig();

        loader.unLoadAll();
        loader.loadAll();

        itemManager = new ItemManager();
        guiManager = new GUIManager();

        serverManager = new JuicyServerManager();

    }
}