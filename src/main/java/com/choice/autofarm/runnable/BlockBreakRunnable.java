package com.choice.autofarm.runnable;

import com.choice.autofarm.block_packet.BlockPositionPacket;
import com.choice.autofarm.entity.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakRunnable extends BukkitRunnable {

    private static final int MAX_BLOCK_STATUS = 10;
    private static final int DISTANCE = 2;
    private static final int BREAK_INTERVAL_SECONDS = 20;

    private int blockStatus = 0;
    private Block blockFocused;
    private final EntityArmorStand entityArmorStand;
    private final BlockPlacementTimer placementTimer;
    private final BlockPositionPacket packet;

    private int breakTimer = 0;

    public boolean isActive = false;

    public BlockBreakRunnable(EntityArmorStand entityArmorStand) {
        this.entityArmorStand = entityArmorStand;
        this.packet = new BlockPositionPacket();
        this.placementTimer = new BlockPlacementTimer(entityArmorStand);
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskTimer(@NotNull Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        this.isActive = true;
        return super.runTaskTimer(plugin, delay, period);
    }

    @Override
    public void run() {
        executeLookingBlocks();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        isActive = false;
        super.cancel();
    }

    private void clean() {
        blockStatus = -1;
        sendBlockPacket();
        blockStatus = 0;
        blockFocused = null;
        entityArmorStand.cancelAnimateRightArm();
    }

    public void stopRunnable() {
        cancel();
        clean();
    }

    private void executeLookingBlocks() {
        Location location = entityArmorStand.getLocation();

        entityArmorStand.updateName(""+entityArmorStand.getAmount());


        if (blockFocused != null) {
            if (!isValidBlock()) {
                clean();
                return;
            }

            entityArmorStand.rotationBody(blockFocused.getLocation().add(0, 0.1, 0));
            entityArmorStand.rotationHead(blockFocused.getLocation().add(0, 0.1, 0));

            if (blockStatus == MAX_BLOCK_STATUS) {
                collectAndClean();
            } else {
                sendBlockPacket();
                blockStatus++;
            }
            return;
        }

        int y = location.getBlockY() - 1;
        Block block = getRandomBlock(location, y);
        if (areLocationsEqual(block.getLocation(), location)) {
            breakTimer = 0;
            return;
        }

        if (!isValidBlockType(block.getType())) {
            breakTimer++;
            entityArmorStand.updateName("Breaking timer " + breakTimer);
            Location standLocation = entityArmorStand.getPlayerSpawnLocation();
            entityArmorStand.rotationHead(standLocation);
            entityArmorStand.rotationBody(standLocation);
            if(breakTimer == BREAK_INTERVAL_SECONDS){
                entityArmorStand.updateName("Adding block");
                entityArmorStand.stopLookingBlocks();
                entityArmorStand.startPlaceBlocks();
            }
            return;
        }
        breakTimer = 0;
        blockFocused = block;
        entityArmorStand.animateRightArm();
    }


    private Block getRandomBlock(Location location, int y) {
        int randomX = location.getBlockX() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        int randomZ = location.getBlockZ() - DISTANCE + ThreadLocalRandom.current().nextInt(2 * DISTANCE + 1);
        return location.getWorld().getBlockAt(randomX, y, randomZ);
    }

    private boolean isValidBlock() {
        return blockFocused.getType().isSolid() &&
                (blockFocused.getType() == Material.STONE || blockFocused.getType() == Material.COBBLESTONE);
    }

    private void collectAndClean() {
        entityArmorStand.addAmount();
        blockFocused.setType(Material.AIR);
        clean();
    }

    private void sendBlockPacket() {
        if (blockFocused == null) return;
        packet.sendPacket(blockFocused.getLocation(), blockStatus);
    }

    private boolean isValidBlockType(Material type) {
        entityArmorStand.cancelAnimateRightArm();
        return type == Material.STONE || type == Material.COBBLESTONE;
    }

    private boolean areLocationsEqual(Location loc1, Location loc2) {
        double epsilon = 0.001;
        return Math.abs(loc1.getX() - loc2.getX()) < epsilon &&
                Math.abs(loc1.getY() - loc2.getY()) < epsilon &&
                Math.abs(loc1.getZ() - loc2.getZ()) < epsilon;
    }
}
