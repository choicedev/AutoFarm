package com.choice.autofarm.util;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.concurrent.ThreadLocalRandom;

public class BlocksUtil {

    public static Block getRandomBlock(Location location, int y, int distance, boolean allow_vertical) {
        int randomX = location.getBlockX() - distance + ThreadLocalRandom.current().nextInt(2 * distance + 1);
        int randomY = location.getBlockY() - distance + ThreadLocalRandom.current().nextInt(2 * distance + 1);
        int randomZ = location.getBlockZ() - distance + ThreadLocalRandom.current().nextInt(2 * distance + 1);
        return location.getWorld().getBlockAt(randomX, allow_vertical ? randomY : y, randomZ);
    }

}
