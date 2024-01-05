package com.choice.autofarm.util;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.concurrent.ThreadLocalRandom;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.ALLOW_VERTICAL;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.DISTANCE;

public class BlocksUtil {

    public static Block getRandomBlock(Location location, int y) {
        int randomX = location.getBlockX() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        int randomY = location.getBlockY() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        int randomZ = location.getBlockZ() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        return location.getWorld().getBlockAt(randomX, ALLOW_VERTICAL ? randomY : y, randomZ);
    }

}
