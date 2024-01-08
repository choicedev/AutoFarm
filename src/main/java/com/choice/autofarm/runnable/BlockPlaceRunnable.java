package com.choice.autofarm.runnable;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.domain.MinionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.mineacademy.fo.model.SimpleRunnable;
import org.mineacademy.fo.remain.CompMaterial;

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
        MinionType type = entityArmorStand.getArmorStandType();
        Location entityLocation = entityArmorStand.getLocation();
        Block targetBlock = getRandomBlock(
                type == MinionType.WHEAT ? entityArmorStand.getLocation().add(0, 1, 0) : entityLocation,
                type == MinionType.WHEAT ? entityLocation.getBlockY()  : entityLocation.getBlockY() - 1,
                entityArmorStand.getBreakDistance(),
                entityArmorStand.getAllowBreakVertical()
        );

        if (!hasSpaceAround(type == MinionType.WHEAT ? entityArmorStand.getLocation().add(0, 1, 0) : entityLocation)) {
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
        CompMaterial addMaterial = entityArmorStand.addBlockInWorld(blockLocation);
        if(addMaterial == CompMaterial.AIR) return;
        entityArmorStand.rotateToBlock(blockLocation);
        targetBlock.setType(addMaterial.toMaterial());
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

        int radius = entityArmorStand.getBreakDistance();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = location.getWorld().getBlockAt(location.getBlockX() + x, entityArmorStand.getAllowBreakVertical() ? location.getBlockY() + y : location.getBlockY() - 1, location.getBlockZ() + z);
                    if (block.getType() == Material.AIR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
