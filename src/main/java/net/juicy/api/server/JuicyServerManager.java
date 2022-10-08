package net.juicy.api.server;

import lombok.Value;
import net.juicy.api.utils.util.HibernateUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Value
public class JuicyServerManager extends BukkitRunnable {

    JuicyAPIPlugin plugin;
    Map<String, JuicyServer> servers;

    String currentServerName = Bukkit.getMotd();
    JuicyServer currentServer;

    public JuicyServerManager() {

        plugin = JuicyAPIPlugin.getPlugin();
        servers = new HashMap<>();

        currentServer = loadLocalServer();

        runTaskTimerAsynchronously(plugin, 0L, 5L);

    }

    public JuicyServer getServer(String name) {

        return servers.get(name);

    }

    public int getTotalOnline() {

        AtomicInteger online = new AtomicInteger(0);

        servers.values().forEach(server -> online.addAndGet(server.getPlayers()));

        return online.get();

    }

    public int getMaxOnline() {

        AtomicInteger online = new AtomicInteger(0);

        servers.values().forEach(server -> online.addAndGet(server.getMaxPlayers()));

        return online.get();

    }

    public int getOnlineOnServerGroupByStartName(String serversName) {

        AtomicInteger online = new AtomicInteger(0);

        servers.values()
                .stream()
                .filter(server -> server.getName().startsWith(serversName))
                .forEach(server -> online.addAndGet(server.getPlayers()));

        return online.get();

    }

    public int getOnlineOnServerGroupByNames(List<String> servers) {

        AtomicInteger online = new AtomicInteger(0);

        servers.stream()
                .filter(this.servers::containsKey)
                .forEach(server -> online.addAndGet(this.servers.get(server).getPlayers()));

        return online.get();

    }

    public int getOnlineOnServerGroup(List<JuicyServer> servers) {

        AtomicInteger online = new AtomicInteger(0);

        servers.forEach(server -> online.addAndGet(server.getPlayers()));

        return online.get();

    }

    public JuicyServer loadLocalServer() {

        if (currentServer == null)
            return new JuicyServer(currentServerName,
                    Bukkit.getOnlinePlayers().size(), plugin.getConfig().getInt("maxOnline"),
                    JuicyServerStatus.ENABLED, Bukkit.hasWhitelist() ? JuicyServerState.DEVELOPMENT : JuicyServerState.UNKNOWN, true);
        else if (currentServer.isUpdatable())
            currentServer.setPlayers(Bukkit.getOnlinePlayers().size());

        return currentServer;

    }

    public void loadServer(String name) {

        HibernateUtil.get(JuicyServer.class, name).ifPresent(server -> servers.put(name, server));

    }

    public void loadAllServers() {

        HibernateUtil.createQuery("From JuicyServer", JuicyServer.class)
                .ifPresent(query -> query
                        .list()
                        .forEach(server -> servers.put(server.getName(), server)));

    }

    public boolean serverExists(String name) {

        return HibernateUtil.get(JuicyServer.class, name).isPresent();

    }

    public void createServer(String name) {

        if (!serverExists(name))
            saveServer(new JuicyServer(name, 0, 0, null, null, true));

    }

    public void saveServer(JuicyServer juicyServer) {

        HibernateUtil.saveOrUpdate(juicyServer);

    }

    public boolean tryToConnect(Player player, JuicyServer juicyServer) {

        if (juicyServer.getStatus().equals(JuicyServerStatus.DISABLED))
            return false;
        if (juicyServer.getPlayers() >= juicyServer.getMaxPlayers() && player.hasPermission("juicy.full"))
            return true;
        else if (juicyServer.getState().equals(JuicyServerState.DEVELOPMENT) && player.hasPermission("juicy.developer"))
            return true;
        else if (juicyServer.getState().equals(JuicyServerState.INGAME) && player.hasPermission("juicy.spectate"))
            return true;
        else
            return juicyServer.getPlayers() < juicyServer.getMaxPlayers();

    }

    public void run() {

        loadLocalServer();
        saveServer(currentServer);

        loadAllServers();

    }
}