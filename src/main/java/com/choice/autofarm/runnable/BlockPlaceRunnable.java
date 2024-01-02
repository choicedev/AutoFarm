package com.choice.autofarm.runnable;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.entity.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.mineacademy.fo.model.SimpleRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class BlockPlaceRunnable extends SimpleRunnable {

    private static final int DISTANCE = 2;
    private static final int MAX_PLACEMENT_STATUS = 10;

    private final EntityArmorStand entityArmorStand;
    private int placementStatus = 0;

    public boolean isActive = false;

    public BlockPlaceRunnable(EntityArmorStand entityArmorStand) {
        this.entityArmorStand = entityArmorStand;
    }

    public void runPlaceBlocks() {
        isActive = true;
        runTaskTimer(AutoFarm.getInstance(), 2, 20L);
    }

    public void stopPlaceBlocks() {
        isActive = false;
        placementStatus = 0;
        cancel();
    }

    @Override
    public void run() {
        placeBlocks();
    }

    private void placeBlocks() {
        Location entityLocation = entityArmorStand.getLocation();
        Block targetBlock = getRandomBlock(entityLocation);

        if (!hasSpaceAround(entityLocation.subtract(0, 1, 0))) {
            handleNoSpace();
            return;
        }

        if (targetBlock.getType() == Material.AIR) {
            handleAirBlock(targetBlock);
        } else {
            placementStatus++;
        }

        if (placementStatus == MAX_PLACEMENT_STATUS) {
            handlePlacementCompletion();
        }
    }

    private void handleNoSpace() {
        Location standLocation = entityArmorStand.getLocation();
        entityArmorStand.rotateToBlock(standLocation);
        placementStatus = 0;
        entityArmorStand.startLookingBlocks();
        entityArmorStand.stopPlaceBlocks();
    }

    private void handleAirBlock(Block targetBlock) {
        Location blockLocation = targetBlock.getLocation();
        entityArmorStand.rotateToBlock(blockLocation);
        targetBlock.setType(Material.COBBLESTONE);
        placementStatus = 0;
    }

    private void handlePlacementCompletion() {
        Location standLocation = entityArmorStand.getLocation();
        entityArmorStand.rotateToBlock(standLocation);
        placementStatus = 0;
        entityArmorStand.startLookingBlocks();
        entityArmorStand.stopPlaceBlocks();
    }

    private boolean hasSpaceAround(Location location) {
        int radius = 1;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Block block = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY(), location.getBlockZ() + z);
                if (block.getType() == Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    private Block getRandomBlock(Location location) {
        int randomX = location.getBlockX() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        int randomZ = location.getBlockZ() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        return location.getWorld().getBlockAt(randomX, location.getBlockY() - 1, randomZ);
    }
}
