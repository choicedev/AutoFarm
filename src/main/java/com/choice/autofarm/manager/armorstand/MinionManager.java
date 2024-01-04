package com.choice.autofarm.manager.armorstand;

import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.player.EntityPlayer;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface MinionManager {


    void addMinionToPlayer(UUID uuid, EntityMinion entityMinion);

    boolean containsKey(UUID uuid);

    boolean minionClickedIsFromPlayer(UUID uuid);

    void spawnMinion(EntityPlayer player, Location placeLocation, String entityUuid, MinionType minionType);

    void getItemFromMinion(EntityPlayer player, EntityMinion minion);

    void giveHeadToPlayer(EntityPlayer player, MinionType minionType);

    List<EntityMinion> getListEntityMinionByUUID(UUID uuid);

    EntityMinion getEntityMinionByUUID(UUID uuid, String entityUuid);

    void removeMinion(EntityPlayer entityPlayer, EntityMinion minion);
}
