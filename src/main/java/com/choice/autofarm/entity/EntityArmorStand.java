package com.choice.autofarm.entity;

import com.choice.autofarm.Main;
import com.choice.autofarm.builder.ItemStackBuilder;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.runnable.BlockBreakRunnable;
import com.choice.autofarm.runnable.BlockPlacementTimer;
import com.choice.autofarm.util.MinionConstants;
import com.choice.autofarm.util.armorstand.BodyPart;
import com.choice.autofarm.util.armorstand.BodyPart.Parts;
import com.choice.autofarm.util.hologram.MinionHologram;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EntityArmorStand {

    ArmorStand entity;
    private Location spawnLocation;
    private BlockBreakRunnable blockBreakRunnable;
    private BlockPlacementTimer blockPlacementTimer;
    private EntityMinion entityMinion;
    private MinionHologram minionHologram;
    private BodyPart bodyPart;
    private Location playerLocation;

    public EntityArmorStand(Location location, EntityMinion entityMinion) {
        this.spawnLocation = location.add(0.5, 1, 0.5);
        this.entityMinion = entityMinion;
        blockBreakRunnable = new BlockBreakRunnable(this);
        blockPlacementTimer = new BlockPlacementTimer(this);
        bodyPart = new BodyPart();
    }


    public void createEntityArmorStand() {
        entity = spawnLocation.getWorld().spawn(spawnLocation, ArmorStand.class);
        minionHologram = new MinionHologram("" + entityMinion.getUUID(), entityMinion.getDisplayName());
        entity.setArms(true);
        entity.setSmall(true);
        entity.setBasePlate(false);
        entity.setCustomNameVisible(false);
        addEquipment();
        minionHologram.createHologramName(spawnLocation);

    }


    private void addEquipment() {
        entity.setItemInHand(getHandItem());
        entity.setHelmet(entityMinion.getHead());
        entity.setChestplate(getEquipament(Material.LEATHER_CHESTPLATE));
        entity.setLeggings(getEquipament(Material.LEATHER_LEGGINGS));
        entity.setBoots(getEquipament(Material.LEATHER_BOOTS));

    }

    ItemStack getHandItem() {
        ItemStack pickaxe = new ItemStackBuilder(Material.DIAMOND_PICKAXE)
                .enchant(Enchantment.DURABILITY, 100)
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        NBTItem nbtItem = new NBTItem(pickaxe);
        nbtItem.setString(MinionConstants.ENTITY_MINION_TYPE, entityMinion.getMinionType().name());
        nbtItem.setString(MinionConstants.ENTITY_MINION_OWNER_UUID, "" + entityMinion.getOwner());
        nbtItem.setString(MinionConstants.ENTITY_MINION_NAME, entityMinion.getDisplayName());
        nbtItem.setString(MinionConstants.ENTITY_MINION_UUID, "" + entityMinion.getUUID());
        pickaxe = nbtItem.getItem();
        return pickaxe;
    }

    ItemStack getEquipament(Material material) {
        NBTItem nbtItem = new NBTItem(new ItemStackBuilder(material).build());
        nbtItem.mergeCompound(new NBTContainer("{Trim:{pattern:coast,material:lapis},display:{color:4673362}}"));
        return nbtItem.getItem();
    }


    public void updateName(String name) {
        minionHologram.updateName(name, entity.getLocation());
    }

    public MinionType getAmorStandType() {
        return this.entityMinion.getMinionType();
    }

    public void startLookingBlocks() {
        if (blockBreakRunnable == null) {
            blockBreakRunnable = new BlockBreakRunnable(this);
        }
        blockBreakRunnable.runTaskTimer(Main.getInstance(), 4, 10L);
    }

    public void startPlaceBlocks() {
        if (blockPlacementTimer == null) {
            blockPlacementTimer = new BlockPlacementTimer(this);
        }
        blockPlacementTimer.runTaskTimer(Main.getInstance(), 2, 20L);
    }

    public void addAmount() {
        entityMinion.addAmount();
    }

    public int getAmount() {
        return entityMinion.getAmount();
    }

    public void removeMinion(String entityUuid) {
        minionHologram.deleteName(entityUuid);
        if (blockBreakRunnable != null && blockBreakRunnable.isActive) {
            blockBreakRunnable.stopRunnable();
        }
        if (blockPlacementTimer != null && blockPlacementTimer.isActive) {
            blockPlacementTimer.cancel();
        }
        blockBreakRunnable = null;
        blockPlacementTimer = null;
    }

    public void stopLookingBlocks() {
        blockBreakRunnable.stopRunnable();
        blockBreakRunnable = null;
    }

    public void stopPlaceBlocks() {
        blockPlacementTimer.cancel();
        blockPlacementTimer = null;
    }

    public void rotationHead(Location blockLocation) {
        bodyPart.setBodyPose(Parts.HEAD);
        bodyPart.setPose(entity, blockLocation);
    }

    public void rotationBody(Location blockLocation) {
        bodyPart.setBodyPose(Parts.BODY);
        bodyPart.rotateBody(entity, blockLocation);
    }

    public void animateRightArm() {
        bodyPart.setBodyPose(Parts.RIGHT_ARM);
        bodyPart.animateRightArmAsync(entity);
    }

    public void animateLeftArm() {
        bodyPart.setBodyPose(Parts.LEFT_ARM);
        bodyPart.animateLeftArmAsync(entity);
    }

    public void cancelAnimateRightArm() {
        bodyPart.setBodyPose(Parts.RIGHT_ARM);
        bodyPart.cancelAnimation(entity);
    }

    public void cancelAnimateLeftArm() {
        bodyPart.setBodyPose(Parts.LEFT_ARM);
        bodyPart.cancelAnimation(entity);
    }

    public Location getLocation() {
        return entity.getLocation();
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public Location getPlayerSpawnLocation() {
        return this.playerLocation;
    }

    public void setPlayerLocation(Location location) {
        this.playerLocation = location;
    }

    public String getMinionUUID() {
        return entityMinion.getUUID().toString();
    }

    public ArmorStand getEntity() {
        return entity;
    }

    public Block getTargetBlock() {
        Location eyeLocation = entity.getEyeLocation();
        Vector direction = eyeLocation.getDirection();
        int maxDistance = 2;

        for (int i = 1; i <= maxDistance; i++) {
            Location targetLocation = eyeLocation.clone().add(direction.clone().multiply(i));
            Block targetBlock = targetLocation.getBlock();

            if (targetBlock.getType() != Material.AIR) {
                return targetBlock;
            }
        }

        return null;
    }

}
