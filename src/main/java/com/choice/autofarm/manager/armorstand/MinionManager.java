package com.choice.autofarm.manager.armorstand;

import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.minion.EntityMinion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface MinionManager {


    void addMinionToPlayer(UUID uuid, EntityMinion entityMinion);

    boolean containsKey(UUID uuid);

    boolean minionClickedIsFromPlayer(UUID uuid);

    void spawnMinion(Player player, Location placeLocation, String entityUuid, MinionType minionType);

    void deleteArmor(Player player, String entityUuid, MinionType minionType);

    void giveHeadToPlayer(Player player, MinionType minionType);

    List<EntityMinion> getEntityMinionByUUID(UUID uuid);

    EntityMinion getEntityMinionByUuid(UUID uuid, String entityUuid);
}
