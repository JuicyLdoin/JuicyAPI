package net.juicy.api.server;

import lombok.Value;
import net.juicy.api.utils.util.HibernateUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.entity.Player;

@Value
public class JuicyServerManager implements Runnable {

    JuicyAPIPlugin plugin;
    Map<String, JuicyServer> servers;

    String currentServerName = Bukkit.getMotd();
    JuicyServer currentServer;

    public JuicyServerManager() {

        plugin = JuicyAPIPlugin.getPlugin();
        servers = Collections.synchronizedMap(new HashMap<>());

        currentServer = loadLocalServer();

        new Thread(this).start();

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

    public JuicyServer getByHighestOnline(List<JuicyServer> servers) {

        JuicyServer juicyServer = null;

        for (JuicyServer server : servers)
            if (juicyServer == null)
                juicyServer = server;
            else if (juicyServer.getPlayers() < server.getPlayers())
                juicyServer = server;

        return juicyServer;

    }

    public JuicyServer getByHighestOnlineAndCanConnect(List<JuicyServer> servers, Player player) {

        JuicyServer juicyServer = null;

        for (JuicyServer server : servers)
            if (juicyServer == null)
                juicyServer = server;
            else if (juicyServer.getPlayers() < server.getPlayers() && tryToConnect(player, server))
                juicyServer = server;

        return juicyServer;

    }

    public List<JuicyServer> getLobbyServers() {

        return servers.values()
                .stream()
                .filter(juicyServer -> juicyServer.getName().startsWith("Лобби"))
                .collect(Collectors.toList());

    }

    public JuicyServer getLobbyServerToConnectPlayer(Player player) {

        return getByHighestOnlineAndCanConnect(getLobbyServers(), player);

    }

    public JuicyServer loadLocalServer() {

        if (currentServer == null)
            return new JuicyServer(currentServerName,
                    Bukkit.getOnlinePlayers().size(), plugin.getConfig().getInt("maxOnline"),
                    JuicyServerStatus.ENABLED, Bukkit.hasWhitelist() ? JuicyServerState.DEVELOPMENT : JuicyServerState.UNKNOWN,
                    JuicyServerUpdateFlag.ALL);
        else
            switch (currentServer.getUpdateFlag()) {

                case ALL:
                    currentServer.setPlayers(Bukkit.getOnlinePlayers().size());
                    currentServer.setState(Bukkit.hasWhitelist() ? JuicyServerState.DEVELOPMENT : JuicyServerState.UNKNOWN);

                case ONLY_PLAYERS:
                    currentServer.setPlayers(Bukkit.getOnlinePlayers().size());

                case ONLY_STATE:
                    currentServer.setState(Bukkit.hasWhitelist() ? JuicyServerState.DEVELOPMENT : JuicyServerState.UNKNOWN);

            }

        return currentServer;

    }

    public void loadServer(String name) {

        HibernateUtil.get(JuicyServer.class, name).ifPresent(server -> servers.put(name, server));

    }

    public void loadAllServers() {

        HibernateUtil.createQueryAndCallActionForEach("From JuicyServer", JuicyServer.class, juicyServer ->  servers.put(juicyServer.getName(), juicyServer));

    }

    public boolean serverExists(String name) {

        return HibernateUtil.get(JuicyServer.class, name).isPresent();

    }

    public void createServer(String name) {

        if (!serverExists(name))
            saveServer(new JuicyServer(name, 0, 0, null, null, JuicyServerUpdateFlag.ALL));

    }

    public void saveServer(JuicyServer juicyServer) {

        HibernateUtil.saveOrUpdate(juicyServer);

    }

    public boolean tryToConnect(Player player, JuicyServer juicyServer) {

        if (juicyServer.getStatus().equals(JuicyServerStatus.DISABLED))
            return false;
        if (juicyServer.getPlayers() >= juicyServer.getMaxPlayers() && player.hasPermission("juicy.full"))
            return true;
        else if ((juicyServer.getState().equals(JuicyServerState.DEVELOPMENT) && player.hasPermission("juicy.developer")) || Bukkit.getWhitelistedPlayers().contains(player))
            return true;
        else if (juicyServer.getState().equals(JuicyServerState.INGAME) && player.hasPermission("juicy.spectate"))
            return true;
        else
            return juicyServer.getPlayers() < juicyServer.getMaxPlayers();

    }

    public void run() {

        while (JuicyAPIPlugin.getPlugin().isEnabled()) {

            loadLocalServer();
            saveServer(currentServer);

            loadAllServers();

        }
    }
}