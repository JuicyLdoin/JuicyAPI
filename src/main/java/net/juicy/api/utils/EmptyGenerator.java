package net.juicy.api.utils;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class EmptyGenerator extends ChunkGenerator {

    public @NonNull ChunkData generateChunkData(@NonNull World world, @NonNull Random random, int x, int z, @NonNull BiomeGrid biome) {

        ChunkData chunk = createChunkData(world);

        for (int blockX = 0; blockX < 16; blockX++)
            for (int blockY = 0; blockY < 256; blockY++)
                for (int blockZ = 0; blockZ < 16; blockZ++)
                    chunk.setBlock(blockX, blockY, blockZ, Material.AIR);

        return chunk;

    }
}