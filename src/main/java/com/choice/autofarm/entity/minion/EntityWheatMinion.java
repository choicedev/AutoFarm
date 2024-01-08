package com.choice.autofarm.entity.minion;

import com.choice.autofarm.data.NBTDataHandler;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.EntityMinionNBT;
import com.choice.autofarm.util.FarmConstants;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.StoneSettings.STONE_HAND_ITEM_NBT;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.WheatSettings.*;
import static com.choice.autofarm.util.EntityMinionNBT.*;
import static com.choice.autofarm.util.FarmConstants.MINION_TAG;

public class EntityWheatMinion implements EntityMinion {

    private final UUID ownerUUID;
    private final UUID entityUUID;
    private final String displayName;
    @Getter
    private final String texture;
    private final MinionType minionType;

    private int amount = 0;
    private double level = 0.0;

    private boolean isNew = false;

    public EntityWheatMinion(UUID ownerUUID, UUID entityUUID) {
        this.ownerUUID = ownerUUID;
        this.displayName = WHEAT_NAME;
        this.texture = WHEAT_SKIN;
        this.entityUUID = entityUUID;
        this.minionType = MinionType.WHEAT;
        this.isNew = true;
    }

    public EntityWheatMinion(UUID ownerUUID, UUID entityUUID, MinionType minionType, double level, int amount) {
        this.ownerUUID = ownerUUID;
        this.displayName = WHEAT_NAME;
        this.texture = WHEAT_SKIN;
        this.entityUUID = entityUUID;
        this.minionType = minionType;
        this.amount = amount;
        this.level = level;
        this.isNew = true;
    }

    @Override
    public UUID getUUID() {
        return entityUUID;
    }

    @Override
    public UUID getOwner() {
        return ownerUUID;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public MinionType getMinionType() {
        return minionType;
    }

    @Override
    public ItemStack getHead() {
        return createHeadNBT(
                this.displayName,
                this.entityUUID,
                this.ownerUUID,
                this.minionType,
                this.texture,
                this.getLevel(),
                this.amount,
                this.isNew
        );
    }

    @Override
    public ItemStack getItemHand() {
        ItemStack item = ItemCreator.of(CompMaterial.STONE_HOE, FarmConstants.ENTITY_HAND_ITEM_NAME+minionType.name()).make();
        NBTDataHandler minion_tag = new NBTDataHandler(item, MINION_TAG);
        setItemHandMinionNBT(minion_tag, this, this.isNew);
        setNBTContainer(minion_tag.getNbtItem(), WHEAT_HAND_ITEM_NBT);
        return minion_tag.getNbtItem().getItem();
    }

    @Override
    public Map<String, ItemStack> getArmor() {
        Map<String, ItemStack> map = new HashMap();
        NBTItem nbtChestplate = new NBTItem(ItemCreator.of(CompMaterial.LEATHER_CHESTPLATE).make());
        NBTItem nbtLeggings = new NBTItem(ItemCreator.of(CompMaterial.LEATHER_LEGGINGS).make());
        NBTItem nbtBoots = new NBTItem(ItemCreator.of(CompMaterial.LEATHER_BOOTS).make());
        setNBTContainer(nbtChestplate, WHEAT_ARMOR_NBT);
        setNBTContainer(nbtLeggings, WHEAT_ARMOR_NBT);
        setNBTContainer(nbtBoots, WHEAT_ARMOR_NBT);

        map.put(FarmConstants.ENTITY_CHESTPLATE, nbtChestplate.getItem());
        map.put(FarmConstants.ENTITY_LEGGINGS, nbtLeggings.getItem());
        map.put(FarmConstants.ENTITY_BOOTS, nbtBoots.getItem());
        return map;
    }

    @Override
    public CompMaterial blockFarm() {
        return CompMaterial.WHEAT;
    }

    @Override
    public CompMaterial createBlock(Location location) {
        Location belowBlock = new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ());
        Material materialFarm = belowBlock.getBlock().getType();
        Block block = location.getBlock();
        Block blockFarm = belowBlock.getBlock();
        boolean isBlockFarm = materialFarm == Material.FARMLAND;
        boolean isGrass = materialFarm == Material.GRASS_BLOCK || materialFarm == Material.DIRT;
        if (!isBlockFarm) {
            if(!isGrass) return CompMaterial.AIR;
            blockFarm.setType(Material.FARMLAND);
            return CompMaterial.WHEAT;
        }
        return CompMaterial.WHEAT;
    }

    @Override
    public void addAmount() {
        amount += 1;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean isValidBlocks(Block block) {
        CompMaterial material = CompMaterial.fromBlock(block);
        if (material == CompMaterial.WHEAT) {
            Ageable age = (Ageable) block.getBlockData();
            return age.getAge() == 7;
        } else {
            return false;
        }

    }

    @Override
    public int getBreakDistance() {
        return WHEAT_DISTANCE;
    }

    @Override
    public boolean getAllowBreakVertical() {
        return WHEAT_ALLOW_VERTICAL;
    }

    @Override
    public Double getLevel() {
        return 0.0;
    }

    @Override
    public void updateLevel(Double level) {

    }
}
