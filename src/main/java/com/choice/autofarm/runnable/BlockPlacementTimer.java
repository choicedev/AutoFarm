package com.choice.autofarm.runnable;

import com.choice.autofarm.entity.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class BlockPlacementTimer extends BukkitRunnable {

    private static final int DISTANCE = 2;
    private final EntityArmorStand entityArmorStand;
    private final int MAX_PLACEMENT_STATUS = 10;

    private int placementStatus = 0;

    public boolean isActive = false;

    public BlockPlacementTimer(EntityArmorStand entityArmorStand) {
        this.entityArmorStand = entityArmorStand;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskTimer(@NotNull Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        this.isActive = true;
        return super.runTaskTimer(plugin, delay, period);
    }

    @Override
    public void run() {
        placeBlocks();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        isActive = false;
        super.cancel();
    }

    private void placeBlocks() {


        Location entityLocation = entityArmorStand.getEntity().getLocation();
        Block targetBlock = getRandomBlock(entityLocation);

        if(!hasSpaceAround(entityLocation.subtract(0, 1,0))){
            Location standLocation = entityArmorStand.getPlayerSpawnLocation();
            entityArmorStand.rotationHead(standLocation);
            entityArmorStand.rotationBody(standLocation);
            placementStatus = 0;
            entityArmorStand.startLookingBlocks();
            entityArmorStand.stopPlaceBlocks();
            return;
        }

        if (targetBlock.getType() == Material.AIR) {
            entityArmorStand.rotationBody(targetBlock.getLocation());
            entityArmorStand.rotationHead(targetBlock.getLocation());
            targetBlock.setType(Material.COBBLESTONE);
            placementStatus = 0;
        } else {
            placementStatus++;
        }

        if (placementStatus == MAX_PLACEMENT_STATUS) {
            Location standLocation = entityArmorStand.getLocation();
            entityArmorStand.rotationHead(standLocation);
            entityArmorStand.rotationBody(standLocation);
            placementStatus = 0;
            entityArmorStand.startLookingBlocks();
            entityArmorStand.stopPlaceBlocks();
        }
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
