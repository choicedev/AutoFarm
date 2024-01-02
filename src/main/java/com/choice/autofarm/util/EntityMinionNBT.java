package com.choice.autofarm.util;

import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Optional;
import java.util.UUID;

public class EntityMinionNBT {

    public static MinionType getType(ItemStack item, CompMaterial material) {
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == material.getMaterial())
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getString(FarmConstants.ENTITY_MINION_TYPE))
                .map(MinionType::valueOf)
                .orElse(MinionType.NULL);
    }

    public static String getStringInfo(CompMaterial material, ItemStack item, String info) {
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == material.getMaterial())
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getString(info))
                .orElse("");
    }

    public static Optional<String> getMinionEntityName(ItemStack item) {
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == Material.PLAYER_HEAD)
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getCompound("SkullOwner").getString("Name"));
    }

    public static String getMinionUUID(ItemStack item, CompMaterial material){
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == material.getMaterial())
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getString(FarmConstants.ENTITY_MINION_UUID))
                .orElse("");
    }

    public static String getMinionUUID(ItemStack item){
        return Optional.ofNullable(item)
                .filter(i -> i.getItemMeta().getDisplayName().contains(FarmConstants.ENTITY_HAND_ITEM_NAME))
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getString(FarmConstants.ENTITY_MINION_UUID))
                .orElse("");
    }

    public static void setItemHandMinionNBT(NBTItem nbtItem, EntityMinion minion) {
        nbtItem.setString(FarmConstants.ENTITY_MINION_TYPE, minion.getMinionType().name());
        nbtItem.setString(FarmConstants.ENTITY_MINION_OWNER_UUID, minion.getOwner().toString());
        nbtItem.setString(FarmConstants.ENTITY_MINION_NAME, minion.getDisplayName());
        nbtItem.setString(FarmConstants.ENTITY_MINION_UUID, minion.getUUID().toString());
    }

    public static void setNBTContainer(NBTItem nbtItem, String container){
        nbtItem.mergeCompound(new NBTContainer(container));
    }

    public static ItemStack createHeadNBT(
            String displayName,
            UUID entityUUID,
            UUID ownerUUID,
            MinionType minionType,
            String texture
    ){
        ItemStack head = ItemCreator.of(CompMaterial.PLAYER_HEAD, displayName).make();
        NBTItem nbtItem = new NBTItem(head);
        nbtItem.setString(FarmConstants.ENTITY_MINION_TYPE, minionType.name());
        nbtItem.setString(FarmConstants.ENTITY_MINION_OWNER_UUID, ""+ ownerUUID);
        nbtItem.setString(FarmConstants.ENTITY_MINION_UUID, ""+ entityUUID);
        NBTCompound skull = nbtItem.addCompound("SkullOwner");
        skull.setString("Name", displayName);
        skull.setString("Id", "" + entityUUID);
        NBTListCompound textures = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        textures.setString("Value", texture);
        head = nbtItem.getItem();
        return head;
    }


}
