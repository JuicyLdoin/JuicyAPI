package net.juicy.api.utils.log;

import java.nio.file.Paths;

import lombok.Value;
import org.bukkit.Bukkit;
import java.nio.file.Path;
import java.io.File;
import org.bukkit.scheduler.BukkitRunnable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;
import net.juicy.api.utils.interfaces.ISavable;

@Value
public class JuicyLogger implements ISavable {

    String path;
    List<JuicyLoggerElement> elements;
    
    public JuicyLogger(JavaPlugin plugin) {

        this(plugin, "");

    }
    
    public JuicyLogger(JavaPlugin plugin, String path) {

        this.path = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + (path.equals("") ? "" : ("/" + path));
        elements = new ArrayList<>();

        File file = getFile();

        if (!file.exists())
            file.mkdirs();

        new BukkitRunnable() {

            public void run() {

                save();

            }
        }.runTaskTimerAsynchronously(plugin, 0L, 1200L);
    }

    public Path getPath() {

        return Paths.get(Bukkit.getWorldContainer().getAbsolutePath() + "/JuicyLogs/" + path);

    }
    
    public void addElement(JuicyLoggerElement element) {

        elements.add(element);

    }

    public void save() {

        elements.forEach(JuicyLoggerElement::save);

    }
}