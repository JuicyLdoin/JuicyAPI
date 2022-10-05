package net.juicy.api.utils;

import java.util.ArrayList;

import lombok.Data;
import org.bukkit.entity.ArmorStand;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class Hologram {

    private static final List<Hologram> holograms = new ArrayList<>();

    public static List<Hologram> getHolograms() {

        return holograms;

    }

    private List<String> lines;
    private Location location;

    private List<ArmorStand> spawnedLines;
    
    private Hologram(List<String> lines, Location location) {

        this.lines = lines;
        this.location = location;

        spawnedLines = new ArrayList<>();

    }
    
    public void spawnLine(Location loc, String text) {

        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);

        stand.setCustomName(text);
        stand.setCustomNameVisible(true);

        stand.setVisible(false);
        stand.setGravity(false);
        stand.setBasePlate(false);

        spawnedLines.add(stand);

    }
    
    public void spawn() {

        lines.forEach(line -> spawnLine(location.subtract(0.0, 0.25, 0.0), line));

    }
    
    public void clear() {

        spawnedLines.forEach(Entity::remove);

    }
    
    public void update(List<String> lines) {

        if (spawnedLines.size() > lines.size()) {

            int extraLines = lines.size() - spawnedLines.size();

            List<String> toRemove = new ArrayList<>();

            IntStream.range(0, lines.size() - extraLines)
                    .forEach(line -> toRemove.add(lines.get(line)));

            toRemove.forEach(lines::remove);

            lines.forEach(line -> spawnLine(spawnedLines.get(spawnedLines.size() - 1).getLocation().subtract(0.0, 0.25, 0.0), line));

        }

        for (int linesCount = 0; linesCount < spawnedLines.size(); linesCount++)
            spawnedLines.get(linesCount).setCustomName(lines.get(linesCount));

    }
    
    public void delete() {

        clear();
        Hologram.holograms.remove(this);

    }
    
    public static Hologram createHologram(List<String> lines, Location loc) {

        Hologram holo = new Hologram(lines, loc);
        Hologram.holograms.add(holo);

        return holo;

    }
}