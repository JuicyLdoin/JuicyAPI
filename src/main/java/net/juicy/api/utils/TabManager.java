package net.juicy.api.utils;

import lombok.Data;
import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;
import net.juicy.player.player.JuicyPlayer;
import net.juicy.player.JuicyPlayerPlugin;
import org.bukkit.Bukkit;
import net.juicy.api.utils.placeholder.IPlaceholder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

@Data
public class TabManager extends BukkitRunnable {

    private static TabManager tabManager;

    public static TabManager getTabManager() {

        return tabManager;

    }

    private static JuicyAPIPlugin plugin = JuicyAPIPlugin.getPlugin();
    private static FileConfiguration config = TabManager.plugin.getConfig();

    private IPlaceholder placeholder;
    private String header;
    private String footer;
    private String prefix;
    private String suffix;

    public TabManager(IPlaceholder placeholder) {

        this(placeholder, TabManager.config.getString("tab.header"), TabManager.config.getString("tab.footer"),
                TabManager.config.getString("tab.prefix"), TabManager.config.getString("tab.suffix"));

    }
    
    public TabManager(IPlaceholder placeholder, String header, String footer, String prefix, String suffix) {

        this.placeholder = placeholder;

        this.header = header.replace("%n", "\n");
        this.footer = footer.replace("%n", "\n");

        this.prefix = prefix;
        this.suffix = suffix;

        runTaskTimerAsynchronously(plugin, 0L, 1L);

        tabManager = this;

    }
    
    public void run() {

        Bukkit.getOnlinePlayers().forEach(player -> {

            player.setPlayerListHeaderFooter(placeholder.replace(header, player), placeholder.replace(footer, player));

            Bukkit.getOnlinePlayers().forEach(online -> {

                JuicyPlayer juicyPlayer = JuicyPlayerPlugin.getPlugin().getPlayerManager().getPlayer(online, false);
                Scoreboard scoreboard = player.getScoreboard();

                String teamName = juicyPlayer.getHighestGroup().getPriority() + online.getName();
                Team team = scoreboard.getTeam(teamName);

                if (team == null)
                    team = scoreboard.registerNewTeam(teamName);

                team.setPrefix(placeholder.replace(prefix, online));
                team.setSuffix(placeholder.replace(suffix, online));

                team.addEntry(online.getName());

            });
        });
    }
}
