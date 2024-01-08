package com.choice.autofarm.manager.armorstand;

import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.player.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public interface MinionManager {

    boolean minionClickedIsFromPlayer(UUID uuid, EntityMinion minion);

    void spawnMinion(EntityPlayer player, Location placeLocation, ItemStack head);

    void getItemFromMinion(EntityPlayer player, EntityMinion entityMinion);

    void startEntityArmor(EntityMinion minion, ItemStack item, ArmorStand armorStand);

    void giveHeadToPlayer(EntityPlayer player, MinionType minionType);

    void giveHeadToPlayerMinion(EntityPlayer player, EntityMinion minion);

    void newHeadToPlayer(EntityPlayer player, MinionType minionType);

    void removeMinion(EntityPlayer player, EntityMinion minion);
}
