package com.choice.autofarm.runnable;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.block_packet.BlockPositionPacket;
import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.domain.MinionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.mineacademy.fo.model.SimpleRunnable;

import static com.choice.autofarm.util.BlocksUtil.getRandomBlock;

public class BlockBreakRunnable extends SimpleRunnable {

    private static final int MAX_BLOCK_STATUS = 10;
    private static final int BREAK_INTERVAL_SECONDS = 20;

    private int blockStatus = 0;
    private Block blockFocused;
    private final EntityArmorStand entityArmorStand;
    private final BlockPositionPacket packet;

    private int breakTimer = 0;
    public boolean isActive = false;

    public BlockBreakRunnable(EntityArmorStand entityArmorStand) {
        this.entityArmorStand = entityArmorStand;
        this.packet = new BlockPositionPacket();
    }

    public void runLookingBlocks() {
        isActive = true;
        runTaskTimer(AutoFarm.getInstance(), 4, 10L);
    }

    public void stopLookingBlocks() {
        isActive = false;
        cancel();
    }

    @Override
    public void run() {
        executeLookingBlocks();
    }

    private void clean() {
        blockStatus = -1;
        sendBlockPacket();
        blockStatus = 0;
        blockFocused = null;
        entityArmorStand.cancelAnimateRightArm();
    }

    public void stopRunnable() {
        stopLookingBlocks();
        clean();
    }

    private void executeLookingBlocks() {
        Location location = entityArmorStand.getLocation();
        entityArmorStand.updateName(String.valueOf(entityArmorStand.getAmount()));

        if (blockFocused != null) {
            handleBlockFocus();
        } else {
            handleNoBlockFocus(location);
        }
    }

    private void handleBlockFocus() {
        if (!isValidBlock()) {
            clean();
            return;
        }

        entityArmorStand.rotateToBlock(blockFocused.getLocation().add(0, 0.1, 0));

        if (blockStatus == MAX_BLOCK_STATUS) {
            collectAndClean();
        } else {
            sendBlockPacket();
            blockStatus++;
        }
    }

    private void handleNoBlockFocus(Location location) {
        Block block = getRandomBlock(
                location,
                entityArmorStand.getArmorStandType() == MinionType.WHEAT ? location.getBlockY()  : location.getBlockY() - 1
        );

        if (areLocationsEqual(block.getLocation(), location)) {
            breakTimer = 0;
            return;
        }

        if (!isValidBlockType(block)) {
            handleInvalidBlockType();
        } else {
            breakTimer = 0;
            blockFocused = block;
            entityArmorStand.animateRightArm();
        }
    }

    private void handleInvalidBlockType() {
        breakTimer++;
        entityArmorStand.updateName("Breaking timer " + breakTimer);
        Location standLocation = entityArmorStand.getPlayerSpawnLocation();
        entityArmorStand.rotateToBlock(standLocation);

        if (breakTimer == BREAK_INTERVAL_SECONDS) {
            entityArmorStand.updateName("Adding block");
            entityArmorStand.stopLookingBlocks();
            entityArmorStand.startPlaceBlocks();
        }
    }

    private boolean isValidBlock() {
        return entityArmorStand.isValidBlock(blockFocused);
    }

    private void collectAndClean() {
        entityArmorStand.addAmount();
        blockFocused.setType(Material.AIR);
        clean();
    }

    private void sendBlockPacket() {
        if (blockFocused != null) {
            packet.sendPacket(blockFocused.getLocation(), blockStatus);
        }
    }

    private boolean isValidBlockType(Block block) {
        entityArmorStand.cancelAnimateRightArm();
        return entityArmorStand.isValidBlock(block);
    }

    private boolean areLocationsEqual(Location loc1, Location loc2) {
        double epsilon = 0.001;
        return Math.abs(loc1.getX() - loc2.getX()) < epsilon &&
                Math.abs(loc1.getY() - loc2.getY()) < epsilon &&
                Math.abs(loc1.getZ() - loc2.getZ()) < epsilon;
    }
}
