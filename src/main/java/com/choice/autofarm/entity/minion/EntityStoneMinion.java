package com.choice.autofarm.entity.minion;

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

import static com.choice.autofarm.util.EntityMinionNBT.*;

public class EntityStoneMinion implements EntityMinion {

    private final UUID ownerUUID;
    private final UUID entityUUID;
    private final String displayName;
    @Getter
    private final String texture;
    private final MinionType minionType;

    private int amount = 0;
    public EntityStoneMinion(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        this.displayName = "<#ed9e00>Farm <bold><#009999>STONE</#009999></bold>";
        this.texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTUwM2MwMDg5MDMyNjYwNTA2N2ZmMTZmZmZiYzJkMDUwMjI1MWIzNjgwYzFhY2MxZDY4Y2QzZDA2NGQwNTc3In19fQ==";
        this.minionType = MinionType.STONE;
        entityUUID = UUID.randomUUID();
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
                this.texture
        );
    }

    @Override
    public ItemStack getItemHand() {
        NBTItem nbtItem = new NBTItem(ItemCreator.of(CompMaterial.DIAMOND_PICKAXE, FarmConstants.ENTITY_HAND_ITEM_NAME+minionType.name()).make());
        setNBTContainer(nbtItem, "{Unbreakable:1,Enchantments:[{id:channeling,lvl:1},{id:vanishing_curse,lvl:1},{id:efficiency,lvl:7},{id:fire_aspect,lvl:4},{id:fire_protection,lvl:5},{id:flame,lvl:1},{id:unbreaking,lvl:7}]}");
        setItemHandMinionNBT(nbtItem, this);
        return nbtItem.getItem();
    }


    @Override
    public Map<String, ItemStack> getArmor() {
        Map<String, ItemStack> map = new HashMap();
        NBTItem nbtChestplate = new NBTItem(ItemCreator.of(CompMaterial.LEATHER_CHESTPLATE).make());
        NBTItem nbtLeggings = new NBTItem(ItemCreator.of(CompMaterial.LEATHER_LEGGINGS).make());
        NBTItem nbtBoots = new NBTItem(ItemCreator.of(CompMaterial.LEATHER_BOOTS).make());
        setNBTContainer(nbtChestplate, "{Trim:{pattern:coast,material:lapis},display:{color:4673362}}");
        setNBTContainer(nbtLeggings, "{Trim:{pattern:coast,material:lapis},display:{color:4673362}}");
        setNBTContainer(nbtBoots, "{Trim:{pattern:coast,material:lapis},display:{color:4673362}}");

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
}
