package com.choice.autofarm.manager.armorstand;

import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.minion.EntityMinion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import java.util.HashMap;
import java.util.UUID;

import static com.choice.autofarm.factory.MinionFactory.createEntityMinion;

public class IMinionManager implements MinionManager {

    private Map<UUID, List<EntityMinion>> entityMinionsMap = new HashMap<>();
    private Map<UUID, List<EntityArmorStand>> entityArmorStandsMap = new HashMap<>();

    @Override
    public void addMinionToPlayer(UUID uuid, EntityMinion entityMinion) {
        entityMinionsMap.computeIfAbsent(uuid, key -> new ArrayList<>()).add(entityMinion);
    }

    public void addArmorStandsToPlayer(UUID uuid, EntityArmorStand entityMinion) {
        entityArmorStandsMap.computeIfAbsent(uuid, key -> new ArrayList<>()).add(entityMinion);
    }

    @Override
    public boolean containsKey(UUID uuid) {
        return entityMinionsMap.containsKey(uuid);
    }

    @Override
    public boolean minionClickedIsFromPlayer(UUID uuid) {
        return entityMinionsMap.getOrDefault(uuid, Collections.emptyList())
                .stream()
                .anyMatch(minion -> minion.getOwner().equals(uuid));
    }

    @Override
    public void spawnMinion(Player player, Location placeLocation, String entityUuid, MinionType minionType) {

        try {
            EntityMinion minion;
            if(getEntityMinionByUuid(player.getUniqueId(), entityUuid) == null) {
                minion = createEntityMinion(player.getUniqueId(), minionType);
                addMinionToPlayer(player.getUniqueId(), minion);
            }else {
                minion = getEntityMinionByUuid(player.getUniqueId(), entityUuid);
            }
            EntityArmorStand armorStand = new EntityArmorStand(placeLocation, minion);
            armorStand.createEntityArmorStand();
            armorStand.setPlayerLocation(player.getLocation());
            armorStand.rotationBody(player.getLocation());
            armorStand.startLookingBlocks();
            addArmorStandsToPlayer(player.getUniqueId(), armorStand);
        } catch (Exception e) {
            e.printStackTrace();
            giveHeadToPlayer(player, minionType);
        }

    }

    @Override
    public void deleteArmor(Player player, String entityUuid, MinionType minionType) {
        entityArmorStandsMap.getOrDefault(player.getUniqueId(), Collections.emptyList())
                .stream()
                .filter(filter -> filter.getMinionUUID().equals(entityUuid))
                .findFirst()
                .ifPresent(entityArmorStand -> {
                     deleteMinion(player.getUniqueId(), entityUuid);
                     entityArmorStand.cancelAnimateRightArm();
                    entityArmorStand.removeMinion(entityUuid);
                    giveHeadToPlayer(player, minionType);
                });

    }

    @Override
    public void giveHeadToPlayer(Player player, MinionType minionType) {
        EntityMinion minion = createEntityMinion(player.getUniqueId(), minionType);
        addMinionToPlayer(player.getUniqueId(), minion);
        player.getInventory().addItem(minion.getHead());
    }

    private void deleteMinion(UUID uuid, String entityUuid) {
        List<EntityMinion> entityMinions = getEntityMinionByUUID(uuid);
        entityMinions.removeIf(map -> map.getUUID().toString().equals(entityUuid));
        entityArmorStandsMap.get(uuid).removeIf(map -> map.getMinionUUID().equals(entityUuid));
    }

    @Override
    public List<EntityMinion> getEntityMinionByUUID(UUID uuid) {
        return entityMinionsMap.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public EntityMinion getEntityMinionByUuid(UUID uuid, String entityUuid) {
        return getEntityMinionByUUID(uuid)
                .stream()
                .filter(minion -> minion.getUUID().toString().equals(entityUuid))
                .findFirst()
                .orElse(null);
    }
}