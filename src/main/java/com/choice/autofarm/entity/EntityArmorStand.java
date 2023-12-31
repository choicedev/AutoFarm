package com.choice.autofarm.entity;

import com.choice.autofarm.Main;
import com.choice.autofarm.builder.ItemStackBuilder;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.runnable.BlockBreakRunnable;
import com.choice.autofarm.util.MinionConstants;
import com.choice.autofarm.util.armorstand.BodyPart;
import com.choice.autofarm.util.armorstand.BodyPart.Parts;
import com.choice.autofarm.util.hologram.MinionHologram;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class EntityArmorStand {

    ArmorStand entity;
    private Location spawnLocation;
    private BlockBreakRunnable blockBreakRunnable;
    private EntityMinion entityMinion;
    private MinionHologram minionHologram;

    private BodyPart bodyPart;
    public EntityArmorStand(Location location, EntityMinion entityMinion) {
        this.spawnLocation = location.add(0.5, 1, 0.5);
        this.entityMinion = entityMinion;
        blockBreakRunnable = new BlockBreakRunnable(this);
        bodyPart = new BodyPart();
    }


    public void createEntityArmorStand() {
        entity = spawnLocation.getWorld().spawn(spawnLocation, ArmorStand.class);
        minionHologram = new MinionHologram(""+entityMinion.getUUID(), entityMinion.getDisplayName());
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

    ItemStack getHandItem(){
        ItemStack pickaxe = new ItemStackBuilder(Material.DIAMOND_PICKAXE)
                .enchant(Enchantment.DURABILITY, 100)
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        NBTItem nbtItem = new NBTItem(pickaxe);
        nbtItem.setString(MinionConstants.ENTITY_MINION_TYPE, entityMinion.getMinionType().name());
        nbtItem.setString(MinionConstants.ENTITY_MINION_OWNER_UUID, ""+entityMinion.getOwner());
        nbtItem.setString(MinionConstants.ENTITY_MINION_NAME, entityMinion.getDisplayName());
        nbtItem.setString(MinionConstants.ENTITY_MINION_UUID, ""+entityMinion.getUUID());
        pickaxe = nbtItem.getItem();
        return pickaxe;
    }

    ItemStack getEquipament(Material material){
        NBTItem nbtItem = new NBTItem(new ItemStackBuilder(material).build());
        nbtItem.mergeCompound(new NBTContainer("{Trim:{pattern:coast,material:lapis},display:{color:4673362}}"));
        return nbtItem.getItem();
    }


    public void updateName(String name) {
        minionHologram.updateName(Integer.parseInt(name + getAmount()), entity.getLocation());
    }

    public MinionType getAmorStandType() {
        return this.entityMinion.getMinionType();
    }

    public void startLookingBlocks() {
        blockBreakRunnable.runTaskTimer(Main.getInstance(), 4, 10L);
    }

    public void addAmount() {
        entityMinion.addAmount();
    }

    public int getAmount() {
        return entityMinion.getAmount();
    }

    public void stopLookingBlocks(String entityUuid) {
        minionHologram.deleteName(entityUuid);
        blockBreakRunnable.stopRunnable();
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
        bodyPart.animateLeftArmAsync(entity);
    }

    public void cancelAnimateRightArm() {
        bodyPart.setBodyPose(Parts.RIGHT_ARM);
        bodyPart.cancelAnimation(entity);
    }

    public Location getSpawnLocation() {
        return entity.getLocation();
    }

    public String getMinionUUID() {
        return entityMinion.getUUID().toString();
    }

    public ArmorStand getEntity() {
        return entity;
    }
}
