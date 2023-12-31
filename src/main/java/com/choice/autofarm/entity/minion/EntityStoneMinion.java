package com.choice.autofarm.entity.minion;

import com.choice.autofarm.builder.ItemStackBuilder;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.MinionConstants;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EntityStoneMinion implements EntityMinion {

    private UUID ownerUUID;
    private UUID entityUUID;
    private String displayName;
    private String texture;
    private MinionType minionType;

    private int amount = 0;
    public EntityStoneMinion(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        this.displayName = "§6Farm Stone";
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
    public String getTexture() {
        return texture;
    }

    @Override
    public MinionType getMinionType() {
        return minionType;
    }

    @Override
    public ItemStack getHead() {
        ItemStack head = new ItemStackBuilder(Material.PLAYER_HEAD).build();
        NBTItem nbtItem = new NBTItem(head);
        nbtItem.setString(MinionConstants.ENTITY_MINION_TYPE, minionType.name());
        nbtItem.setString(MinionConstants.ENTITY_MINION_OWNER_UUID, ""+ ownerUUID);
        nbtItem.setString(MinionConstants.ENTITY_MINION_UUID, ""+ entityUUID);
        NBTCompound display = nbtItem.addCompound("display");
        display.setString(MinionConstants.ENTITY_MINION_NAME, displayName);
        NBTCompound skull = nbtItem.addCompound("SkullOwner");
        skull.setString("Name", displayName);
        skull.setString("Id", "" + entityUUID);
        NBTListCompound textures = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        textures.setString("Value", texture);
        head = nbtItem.getItem();
        return head;
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
}
