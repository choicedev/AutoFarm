package com.choice.autofarm.runnable;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.domain.MinionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.mineacademy.fo.model.SimpleRunnable;

import java.util.concurrent.ThreadLocalRandom;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.ALLOW_VERTICAL;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.DISTANCE_FARM;
import static com.choice.autofarm.util.BlocksUtil.getRandomBlock;

public class BlockPlaceRunnable extends SimpleRunnable {

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
        Block targetBlock = getRandomBlock(
                entityLocation,
                entityArmorStand.getArmorStandType() == MinionType.WHEAT ? entityLocation.getBlockY() : entityLocation.getBlockY() - 1
        );

        if (!hasSpaceAround(entityLocation)) {
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
        targetBlock.setType(entityArmorStand.addBlockInWorld(blockLocation).toMaterial());
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
        int radius = DISTANCE_FARM;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = location.getWorld().getBlockAt(location.getBlockX() + x, ALLOW_VERTICAL ? location.getBlockY() + y : location.getBlockY() - 1, location.getBlockZ() + z);
                    if (block.getType() == Material.AIR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
