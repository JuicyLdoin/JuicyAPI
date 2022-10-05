package net.juicy.api.utils.util.builders;

import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicReference;

public class BossBarBuilder implements IBuilder<BossBar> {

    private final BossBar bossBar;
    private int displayTaskId = -1;

    public BossBarBuilder(String title) {

        this.bossBar = Bukkit.createBossBar(title, BarColor.PURPLE, BarStyle.SOLID);
        bossBar.show();

    }

    public BossBarBuilder(BossBar bossBar) {

        this.bossBar = bossBar;
        bossBar.show();

    }

    public BossBarBuilder setTitle(String title) {

        bossBar.setTitle(title);
        return this;

    }

    public BossBarBuilder setColor(BarColor barColor) {

        bossBar.setColor(barColor);
        return this;

    }

    public BossBarBuilder setStyle(BarStyle barStyle) {

        bossBar.setStyle(barStyle);
        return this;

    }

    public BossBarBuilder setProgress(double progress) {

        bossBar.setProgress(progress);
        return this;

    }

    public BossBarBuilder setVisible(boolean visible) {

        bossBar.setVisible(visible);
        return this;

    }

    public BossBarBuilder addPlayer(Player player) {

        bossBar.addPlayer(player);
        return this;

    }

    public BossBarBuilder removePlayer(Player player) {

        bossBar.removePlayer(player);
        return this;

    }

    public BossBarBuilder addFlag(BarFlag barFlag) {

        bossBar.addFlag(barFlag);
        return this;

    }

    public BossBarBuilder removeFlag(BarFlag barFlag) {

        bossBar.removeFlag(barFlag);
        return this;

    }

    public BossBarBuilder setHideTime(int time) {

        new BukkitRunnable() {

            public void run() {

                bossBar.hide();

                if (displayTaskId != -1)
                    Bukkit.getScheduler().cancelTask(displayTaskId);

            }
        }.runTaskLater(JuicyAPIPlugin.getPlugin(), time);

        return this;

    }

    public <T> BossBarBuilder setDisplayValue(AtomicReference<T> value) {

        displayTaskId = new BukkitRunnable() {

            public void run() {

                bossBar.setTitle(value.toString());

            }
        }.runTaskTimer(JuicyAPIPlugin.getPlugin(), 0, 1).getTaskId();

        return this;

    }

    public BossBar build() {

        return bossBar;

    }
}