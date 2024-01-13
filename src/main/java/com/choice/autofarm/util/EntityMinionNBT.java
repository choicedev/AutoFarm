package com.choice.autofarm.util;

import com.choice.autofarm.data.NBTDataHandler;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.factory.MinionFactory;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Optional;
import java.util.UUID;

import static com.choice.autofarm.util.FarmConstants.MINION_TAG;

public class EntityMinionNBT {

    public static MinionType getType(ItemStack item) {
        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
        String type = data.getString(FarmConstants.ENTITY_MINION_TYPE);
        return MinionType.valueOf(type);
    }

    public static void setItemHandMinionNBT(NBTDataHandler minion_tag, EntityMinion minion, boolean isCrafted) {
        minion_tag.setBoolean(FarmConstants.ENTITY_CRAFTED, isCrafted);
        minion_tag.setString(FarmConstants.ENTITY_MINION_TYPE, minion.getMinionType().name());
        minion_tag.setString(FarmConstants.ENTITY_MINION_OWNER_UUID, ""+minion.getOwner());
        minion_tag.setString(FarmConstants.ENTITY_MINION_UUID, ""+ minion.getUUID());
        minion_tag.setDouble(FarmConstants.ENTITY_MINION_LEVEL, minion.getLevel());
        minion_tag.setInt(FarmConstants.ENTITY_MINION_BLOCKS, minion.getAmount());
    }

    public static void setNBTContainer(NBTItem nbtItem, String container){
        nbtItem.mergeCompound(new NBTContainer(container));
    }

    public static void addAmountMinion(ItemStack item, int amount){
        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
        data.setInt(FarmConstants.ENTITY_MINION_BLOCKS, amount);
    }

    public static int getAmountMinion(ItemStack item){
        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
        return data.getInt(FarmConstants.ENTITY_MINION_BLOCKS);
    }

    public static void setLocation(ItemStack item, Location location){
        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
        data.setLocation(location, null);
    }

    public static Location getLocation(ItemStack item){
        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
        return data.getLocation();
    }

    public static EntityMinion getEntityMinion(ItemStack item) {

        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
        String ownerUUID = data.getString(FarmConstants.ENTITY_MINION_OWNER_UUID);
        String entityUUID = data.getString(FarmConstants.ENTITY_MINION_UUID);
        MinionType minionType = MinionType.valueOf(data.getString(FarmConstants.ENTITY_MINION_TYPE));
        double level = data.getDouble(FarmConstants.ENTITY_MINION_LEVEL);
        int amount = data.getInt(FarmConstants.ENTITY_MINION_BLOCKS);

        return MinionFactory.getEntityMinion(ownerUUID, entityUUID, minionType, level, amount);
    }

    public static boolean hasNBT(NBTItem item){
        return item.getCompound(MINION_TAG) != null;
    }

    private static NBTCompound getNBTMinionTag(NBTItem item){
        return item.getCompound(MINION_TAG);
    }


    public static ItemStack createHeadNBT(
            String displayName,
            UUID entityUUID,
            UUID ownerUUID,
            MinionType minionType,
            String texture,
            Double level,
            Integer blocks,
            boolean isNew
    ){
        ItemStack head = ItemCreator.of(CompMaterial.PLAYER_HEAD, displayName).make();
        NBTDataHandler minion_tag = new NBTDataHandler(head, MINION_TAG);
        minion_tag.setBoolean(FarmConstants.ENTITY_CRAFTED, isNew);
        minion_tag.setString(FarmConstants.ENTITY_MINION_TYPE, minionType.name());
        minion_tag.setString(FarmConstants.ENTITY_MINION_OWNER_UUID, ""+ ownerUUID);
        minion_tag.setString(FarmConstants.ENTITY_MINION_UUID, ""+ entityUUID);
        minion_tag.setDouble(FarmConstants.ENTITY_MINION_LEVEL, level);
        minion_tag.setInt(FarmConstants.ENTITY_MINION_BLOCKS, blocks);
        NBTCompound skull = minion_tag.getNbtItem().addCompound("SkullOwner");
        skull.setString("Name", displayName);
        skull.setString("Id", "" + entityUUID);
        NBTListCompound textures = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        textures.setString("Value", texture);
        head = minion_tag.getNbtItem().getItem();
        return head;
    }


}
