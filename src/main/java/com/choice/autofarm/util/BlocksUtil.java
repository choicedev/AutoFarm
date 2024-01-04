package com.choice.autofarm.util;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.concurrent.ThreadLocalRandom;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.ALLOW_VERTICAL;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.DISTANCE_FARM;

public class BlocksUtil {

    public static Block getRandomBlock(Location location, int y) {
        int randomX = location.getBlockX() - DISTANCE_FARM + ThreadLocalRandom.current().nextInt(2 * DISTANCE_FARM + 1);
        int randomY = location.getBlockY() - DISTANCE_FARM + ThreadLocalRandom.current().nextInt(2 * DISTANCE_FARM + 1);
        int randomZ = location.getBlockZ() - DISTANCE_FARM + ThreadLocalRandom.current().nextInt(2 * DISTANCE_FARM + 1);
        return location.getWorld().getBlockAt(randomX, ALLOW_VERTICAL ? randomY : y, randomZ);
    }

}
