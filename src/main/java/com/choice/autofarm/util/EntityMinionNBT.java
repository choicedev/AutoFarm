package com.choice.autofarm.util;

import com.choice.autofarm.entity.minion.domain.MinionType;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class EntityMinionNBT {

    public static Optional<MinionType> getType(Material material, ItemStack item) {
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == material)
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getString(MinionConstants.ENTITY_MINION_TYPE))
                .map(MinionType::valueOf);
    }

    public static Optional<String> getStringInfo(Material material, ItemStack item, String info) {
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == material)
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getString(info));
    }

    public static Optional<String> getMinionEntityName(ItemStack item) {
        return Optional.ofNullable(item)
                .filter(i -> i.getType() == Material.PLAYER_HEAD)
                .map(NBTItem::new)
                .map(nbtItem -> nbtItem.getCompound("SkullOwner").getString("Name"));
    }


}
