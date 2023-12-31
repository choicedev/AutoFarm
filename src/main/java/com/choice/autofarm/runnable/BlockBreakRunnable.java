package com.choice.autofarm.runnable;

import com.choice.autofarm.Main;
import com.choice.autofarm.block_packet.BlockPositionPacket;
import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.util.armorstand.MiningAnimation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockBreakRunnable extends BukkitRunnable {

    private final int MAX_BLOCK_STATUS = 10;
    private final int DISTANCE = 2;

    private int blockStatus = 0;
    private Block blockFocused;
    private final EntityArmorStand entityArmorStand;
    private final BlockPositionPacket packet;

    public BlockBreakRunnable(EntityArmorStand entityArmorStand) {
        this.entityArmorStand = entityArmorStand;
        this.packet = new BlockPositionPacket();
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

    public void stopRunnable(){
        cancel();
        clean();
    }

    private void executeLookingBlocks() {
        Location location = entityArmorStand.getSpawnLocation();
        entityArmorStand.updateName("");

        if (blockFocused != null) {
            if (!isValidBlock()) {
                clean();
                return;
            }

            entityArmorStand.rotationBody(blockFocused.getLocation());

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
        if(areLocationsEqual()) return;
        if(!isValidBlockType(block.getType())) return;

        blockFocused = block;
        entityArmorStand.animateRightArm();
    }

    private Block getRandomBlock(Location location, int y) {
        int randomX = location.getBlockX() - DISTANCE + (int) (Math.random() * (2 * DISTANCE + 1));
        int randomZ = location.getBlockZ() - DISTANCE + (int) (Math.random() * (2 * DISTANCE + 1));
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
        if(blockFocused == null) return;
        packet.sendPacket(blockFocused.getLocation(), blockStatus);
    }

    private boolean isValidBlockType(Material type) {
        entityArmorStand.cancelAnimateRightArm();
        return type == Material.STONE || type == Material.COBBLESTONE;
    }

    private boolean areLocationsEqual(Location loc1, Location loc2) {
        double epsilon = 0.001; // Pequena margem de erro
        return Math.abs(loc1.getX() - loc2.getX()) < epsilon &&
                Math.abs(loc1.getY() - loc2.getY()) < epsilon &&
                Math.abs(loc1.getZ() - loc2.getZ()) < epsilon;
    }
}

