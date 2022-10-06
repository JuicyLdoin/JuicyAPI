package net.juicy.api.utils.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class EmptyGeneratorUtil extends ChunkGenerator {

    public boolean shouldGenerateNoise() {

        return false;

    }

    public boolean shouldGenerateSurface() {

        return false;

    }

    public boolean shouldGenerateBedrock() {

        return false;

    }

    public boolean shouldGenerateCaves() {

        return false;

    }

    public boolean shouldGenerateDecorations() {

        return false;

    }

    public boolean shouldGenerateMobs() {

        return false;

    }

    public boolean shouldGenerateStructures() {

        return false;

    }

    public Location getFixedSpawnLocation(World world, Random random) {

        return new Location(world, 0, 100, 0);

    }
}