package com.choice.autofarm.manager.armorstand;

import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.player.EntityPlayer;
import org.bukkit.Location;
import org.mineacademy.fo.menu.model.ItemCreator;

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
    public void spawnMinion(EntityPlayer player, Location placeLocation, String entityUuid, MinionType minionType) {

        EntityMinion minion;
        if(getEntityMinionByUUID(player.getUniqueId(), entityUuid) == null) {
            minion = createEntityMinion(player.getUniqueId(), minionType);
            addMinionToPlayer(player.getUniqueId(), minion);
        }else {
            minion = getEntityMinionByUUID(player.getUniqueId(), entityUuid);
        }
        EntityArmorStand armorStand = new EntityArmorStand(placeLocation, minion);
        armorStand.createEntityArmorStand();
        armorStand.setPlayerLocation(player.getLocation());
        armorStand.rotationBody(player.getLocation());
        armorStand.startLookingBlocks();
        addArmorStandsToPlayer(player.getUniqueId(), armorStand);

    }

    @Override
    public void removeMinion(EntityPlayer player, EntityMinion minion) {
        entityArmorStandsMap.getOrDefault(player.getUniqueId(), Collections.emptyList())
                .stream()
                .filter(filter -> filter.getMinionUUID().equals(minion.getUUID()))
                .findFirst()
                .ifPresent(entityArmorStand -> {
                     removeMinionFromList(player.getUniqueId(), minion.getUUID());
                     entityArmorStand.cancelAnimateRightArm();
                    entityArmorStand.stopRunnable(minion.getUUID());
                    giveHeadToPlayer(player, minion.getMinionType());
                    player.addItemsOrDrop(ItemCreator.of(minion.blockFarm()).amount(minion.getAmount()).make());
                    minion.setAmount(0);
                    player.sendMessage("<gold><farm> removed from world", map -> map.put("farm", minion.getDisplayName()));
                });

    }

    @Override
    public void giveHeadToPlayer(EntityPlayer player, MinionType minionType) {
        EntityMinion minion = createEntityMinion(player.getUniqueId(), minionType);
        addMinionToPlayer(player.getUniqueId(), minion);
        player.addItemsOrDrop(minion.getHead());
    }

    private void removeMinionFromList(UUID uuid, UUID entityUuid) {
        List<EntityMinion> entityMinions = getListEntityMinionByUUID(uuid);
        entityMinions.removeIf(map -> map.getUUID().equals(entityUuid));
        entityArmorStandsMap.get(uuid).removeIf(map -> map.getMinionUUID().equals(entityUuid));
    }

    @Override
    public List<EntityMinion> getListEntityMinionByUUID(UUID uuid) {
        return entityMinionsMap.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public EntityMinion getEntityMinionByUUID(UUID uuid, String entityUuid) {
        return getListEntityMinionByUUID(uuid)
                .stream()
                .filter(minion -> minion.getUUID().toString().equals(entityUuid))
                .findFirst()
                .orElse(null);
    }
}