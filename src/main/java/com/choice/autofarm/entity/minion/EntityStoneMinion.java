package com.choice.autofarm.entity.minion;

import com.choice.autofarm.data.NBTDataHandler;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.FarmConstants;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.StoneSettings.*;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.WheatSettings.WHEAT_HAND_ITEM_NBT;
import static com.choice.autofarm.util.EntityMinionNBT.*;
import static com.choice.autofarm.util.FarmConstants.MINION_TAG;

public class EntityStoneMinion implements EntityMinion {

    private final UUID ownerUUID;
    private final UUID entityUUID;
    private final String displayName;
    @Getter
    private final String texture;
    private final MinionType minionType;
    private Double level = 0.0;
    private int amount = 0;


    private boolean isNew = false;
    public EntityStoneMinion(UUID ownerUUID, UUID entityUUID) {
        this.ownerUUID = ownerUUID;
        this.displayName = STONE_NAME;
        this.texture = STONE_SKIN;
        this.minionType = MinionType.STONE;
        this.entityUUID = entityUUID;
        this.isNew = true;
    }

    public EntityStoneMinion(UUID ownerUUID, UUID entityUUID, MinionType minionType, double level, int amount) {
        this.ownerUUID = ownerUUID;
        this.displayName = STONE_NAME;
        this.texture = STONE_SKIN;
        this.minionType = minionType;
        this.entityUUID = entityUUID;
        this.level = level;
        this.amount = amount;
        this.isNew = false;
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
                this.level,
                this.amount,
                this.isNew
        );
    }

    @Override
    public ItemStack getItemHand() {
        ItemStack item = ItemCreator.of(CompMaterial.STONE_PICKAXE, FarmConstants.ENTITY_HAND_ITEM_NAME+minionType.name()).make();
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
        setNBTContainer(nbtChestplate, STONE_ARMOR_NBT);
        setNBTContainer(nbtLeggings, STONE_ARMOR_NBT);
        setNBTContainer(nbtBoots, STONE_ARMOR_NBT);

        map.put(FarmConstants.ENTITY_CHESTPLATE, nbtChestplate.getItem());
        map.put(FarmConstants.ENTITY_LEGGINGS, nbtLeggings.getItem());
        map.put(FarmConstants.ENTITY_BOOTS, nbtBoots.getItem());
        return map;
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
        return block.getType().isSolid()  && material == CompMaterial.STONE || material == CompMaterial.COBBLESTONE;
    }

    @Override
    public CompMaterial blockFarm() {
        return CompMaterial.COBBLESTONE;
    }

    @Override
    public CompMaterial createBlock(Location location) {
        return CompMaterial.COBBLESTONE;
    }

    @Override
    public int getBreakDistance() {
        return STONE_DISTANCE;
    }

    @Override
    public boolean getAllowBreakVertical() {
        return STONE_ALLOW_VERTICAL;
    }

    @Override
    public Double getLevel() {
        return 0.0;
    }

    @Override
    public void updateLevel(Double level) {
        this.level = level;
    }
}
