package net.juicy.api.utils;

import java.util.HashMap;

import lombok.Data;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.stream.IntStream;
import org.bukkit.scoreboard.DisplaySlot;
import java.util.Objects;
import org.bukkit.Bukkit;
import net.juicy.api.utils.placeholder.IPlaceholder;
import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.entity.Player;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;

@Data
public class ScoreboardManager extends BukkitRunnable {

    private static Map<Player, ScoreboardManager> scoreboards = new HashMap<>();

    public static ScoreboardManager getManager(Player player) {

        return ScoreboardManager.scoreboards.get(player);

    }

    private static char[] hex= new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    JuicyAPIPlugin plugin;
    Player player;
    String updater;
    IPlaceholder placeholder;

    public static ScoreboardManager createForPlayer(Player player, String updater, IPlaceholder placeholder) {

        ScoreboardManager scoreboardManager = new ScoreboardManager(player, updater, placeholder);
        ScoreboardManager.scoreboards.put(player, scoreboardManager);

        return scoreboardManager;

    }
    
    private ScoreboardManager(Player player, String updater, IPlaceholder placeholder) {

        plugin = JuicyAPIPlugin.getPlugin();

        this.player = player;
        this.updater = updater;
        this.placeholder = placeholder;

        runTaskTimerAsynchronously(plugin, 0L, 1L);

    }
    
    public void setUpdater(String updater) {

        this.updater = updater;
        setScoreboard();

    }
    
    public void setPlaceholder(IPlaceholder placeholder) {

        this.placeholder = placeholder;
        setScoreboard();

    }
    
    public void setScoreboard() {

        FileConfiguration config = plugin.getConfig();

        if (!config.contains("scoreboard." + updater))
            return;

        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(updater, "dummy", JuicyAPIPlugin.getPlugin().replace(config.getString("scoreboard." + updater + ".title")));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        IntStream.rangeClosed(1, config.getInt("scoreboard." + updater + ".rowscount")).forEach(count -> {

            String value = config.getString("scoreboard." + updater + ".rows." + count);

            scoreboard.registerNewTeam(String.valueOf(count)).addEntry("§" + ScoreboardManager.hex[count]);
            scoreboard.getTeam(String.valueOf(count)).setSuffix(placeholder.replace(value, player));

            objective.getScore("§" + ScoreboardManager.hex[count]).setScore(count);

        });

        player.setScoreboard(scoreboard);

    }
    
    public void updateScoreboard() {

        FileConfiguration config = plugin.getConfig();
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard.getObjective(updater) != null)
            IntStream.rangeClosed(1, config.getInt("scoreboard." + updater + ".rowscount"))
                    .forEach(count -> scoreboard.getTeam(String.valueOf(count)).setSuffix(placeholder.replace(config.getString("scoreboard." + updater + ".rows." + count), player)));

    }
    
    public void run() {

        if (player == null || !player.isOnline())
            cancel();
        else
            updateScoreboard();

    }
}
