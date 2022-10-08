package net.juicy.api.utils.placeholder.placeholders;

import lombok.AllArgsConstructor;
import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.juicy.api.server.JuicyServer;

@AllArgsConstructor
public class ServerPlaceholder extends APIPlaceholder {

    private static final ServerPlaceholder serverPlaceholder = new ServerPlaceholder(Bukkit.getMotd());

    public static ServerPlaceholder getServerPlaceholder() {

        return serverPlaceholder;

    }

    private final String serverName;

    protected String replace(String string) {

        JuicyServer juicyServer = JuicyAPIPlugin.getPlugin().getServerManager().getServer(serverName);

        if (juicyServer != null)
            string = string
                    .replace("%server%", juicyServer.getName())
                    .replace("%online%", String.valueOf(juicyServer.getPlayers()))
                    .replace("%maxOnline%", String.valueOf(juicyServer.getMaxPlayers()))
                    .replace("%onlineInGroup%", String.valueOf(JuicyAPIPlugin.getPlugin()
                                    .getServerManager()
                                    .getOnlineOnServerGroupByStartName(juicyServer.getName().split("-")[1])))
                    .replace("%status%", juicyServer.getStatus().getDisplayName())
                    .replace("%state%", juicyServer.getState().getDisplayName());

        return string;

    }

    public String replace(String string, Player player) {

        return super.replace(replace(string), player);

    }
}
