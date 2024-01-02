package com.choice.autofarm.entity.minion;

import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.EntityMinionNBT;
import com.choice.autofarm.util.FarmConstants;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.choice.autofarm.util.EntityMinionNBT.createHeadNBT;

public class EntityWheatMinion implements EntityMinion {

    private final UUID ownerUUID;
    private final UUID entityUUID;
    private final String displayName;
    @Getter
    private final String texture;
    private final MinionType minionType;

    private int amount = 0;
    public EntityWheatMinion(UUID ownerUUID){
        this.ownerUUID = ownerUUID;
        this.entityUUID = UUID.randomUUID();
        this.displayName = "Farm WHEAT";
        this.texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjMyNTQ1YTk4OTdjNGYwODA5ZDdlOWQ3ZGU0YjQ5OWNhZmQ4ZDZhNDJhMGVhY2Y0Y2FhYmYzNDg4YWRjYTIxMSJ9fX0=";
        this.minionType = MinionType.WHEAT;
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
        NBTItem nbtItem = new NBTItem(ItemCreator.of(CompMaterial.DIAMOND_HOE, FarmConstants.ENTITY_HAND_ITEM_NAME+minionType.name()).make());
        EntityMinionNBT.setNBTContainer(nbtItem, "{Enchantments:[{id:unbreaking,lvl:11}]}");
        EntityMinionNBT.setItemHandMinionNBT(nbtItem, this);
        return nbtItem.getItem();
    }

    @Override
    public Map<String, ItemStack> getArmor() {
        return new HashMap<>();
    }

    @Override
    public CompMaterial blockFarm() {
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
        return material == CompMaterial.WHEAT;
    }
}
