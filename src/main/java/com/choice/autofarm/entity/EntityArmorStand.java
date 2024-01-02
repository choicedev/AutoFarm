package com.choice.autofarm.entity;

import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.runnable.BlockBreakRunnable;
import com.choice.autofarm.runnable.BlockPlaceRunnable;
import com.choice.autofarm.util.FarmConstants;
import com.choice.autofarm.util.armorstand.BodyPart;
import com.choice.autofarm.util.armorstand.BodyPart.Parts;
import com.choice.autofarm.util.hologram.MinionHologram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

@AllArgsConstructor
public class EntityArmorStand {

    @Getter
    ArmorStand entity;

    @Getter
    private final Location spawnLocation;
    private BlockBreakRunnable blockBreakRunnable;
    private BlockPlaceRunnable blockPlaceRunnable;
    private final EntityMinion entityMinion;
    private MinionHologram minionHologram;
    private final BodyPart bodyPart;

    @Setter
    @Getter
    private Location playerLocation;

    public EntityArmorStand(Location location, EntityMinion entityMinion) {
        this.spawnLocation = location.add(0.5, 1, 0.5);
        this.entityMinion = entityMinion;
        blockBreakRunnable = new BlockBreakRunnable(this);
        blockPlaceRunnable = new BlockPlaceRunnable(this);
        bodyPart = new BodyPart();
    }

    public void createEntityArmorStand() {
        entity = spawnLocation.getWorld().spawn(spawnLocation, ArmorStand.class);
        minionHologram = new MinionHologram(String.valueOf(entityMinion.getUUID()), entityMinion.getDisplayName());
        configureArmorStand();
    }

    private void configureArmorStand() {
        entity.setArms(true);
        entity.setSmall(true);
        entity.setBasePlate(false);
        entity.setCustomNameVisible(false);
        addEquipment();
        minionHologram.createHologramName(spawnLocation);
    }

    private void addEquipment() {

        entity.setItemInHand(entityMinion.getItemHand());
        entity.setHelmet(entityMinion.getHead());

        if(entityMinion.getArmor().isEmpty()) return;

        entity.setChestplate(entityMinion.getArmor().get(FarmConstants.ENTITY_CHESTPLATE));
        entity.setLeggings(entityMinion.getArmor().get(FarmConstants.ENTITY_LEGGINGS));
        entity.setBoots(entityMinion.getArmor().get(FarmConstants.ENTITY_BOOTS));
    }



    public void updateName(String name) {
        minionHologram.updateName(
                MinionHologram.BLOCK_COUNT_LINE,
                name,
                entity.getLocation()
        );
    }

    public MinionType getArmorStandType() {
        return entityMinion.getMinionType();
    }

    public void startLookingBlocks() {
        if (blockBreakRunnable == null) {
            blockBreakRunnable = new BlockBreakRunnable(this);
        }
        blockBreakRunnable.runLookingBlocks();
    }

    public void startPlaceBlocks() {
        if (blockPlaceRunnable == null) {
            blockPlaceRunnable = new BlockPlaceRunnable(this);
        }
        blockPlaceRunnable.runPlaceBlocks();
    }

    public void addAmount() {
        entityMinion.addAmount();
    }

    public int getAmount() {
        return entityMinion.getAmount();
    }

    public void stopRunnable(UUID entityUuid) {
        minionHologram.deleteName(entityUuid);
        stopBlockBreakRunnable();
        stopBlockPlacementTimer();
        blockBreakRunnable = null;
        blockPlaceRunnable = null;
    }

    public void stopLookingBlocks() {
        stopBlockBreakRunnable();
        blockBreakRunnable = null;
    }

    public void stopPlaceBlocks() {
        stopBlockPlacementTimer();
        blockPlaceRunnable = null;
    }

    private void stopBlockBreakRunnable() {
        if (blockBreakRunnable != null && blockBreakRunnable.isActive) {
            blockBreakRunnable.stopRunnable();
        }
    }

    private void stopBlockPlacementTimer() {
        if (blockPlaceRunnable != null && blockPlaceRunnable.isActive) {
            blockPlaceRunnable.stopPlaceBlocks();
        }
    }

    public void rotateToBlock(Location location) {
        rotationBody(location);
    }

    public Location getLocation() {
        return entity.getLocation();
    }

    public Location getPlayerSpawnLocation() {
        return playerLocation;
    }

    public UUID getMinionUUID() {
        return entityMinion.getUUID();
    }

    public void rotationBody(Location blockLocation) {
        bodyPart.setBodyPose(Parts.BODY);
        bodyPart.rotateBody(entity, blockLocation);
    }

    public void animateRightArm() {
        bodyPart.setBodyPose(Parts.RIGHT_ARM);
        bodyPart.animateRightArmAsync(entity);
    }

    public void cancelAnimateRightArm() {
        bodyPart.setBodyPose(Parts.RIGHT_ARM);
        bodyPart.cancelAnimation(entity);
    }

    public boolean isValidBlock(Block block){
        return entityMinion.isValidBlocks(block);
    }
}
