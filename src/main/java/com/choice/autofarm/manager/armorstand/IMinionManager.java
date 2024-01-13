package com.choice.autofarm.manager.armorstand;

import com.choice.autofarm.data.NBTDataHandler;
import com.choice.autofarm.entity.EntityArmorStand;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.player.EntityPlayer;
import com.choice.autofarm.util.EntityMinionNBT;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;

import java.util.*;

import static com.choice.autofarm.factory.MinionFactory.createEntityMinion;
import static com.choice.autofarm.util.EntityMinionNBT.getEntityMinion;
import static com.choice.autofarm.util.FarmConstants.MINION_TAG;

public class IMinionManager implements MinionManager {

    private Map<UUID, EntityArmorStand> entityArmorStandsMap = new HashMap<>();


    private void addArmorStandsToPlayer(UUID uuid, EntityArmorStand armorStandsMap) {
        if(entityArmorStandsMap.containsKey(uuid)) return;
        entityArmorStandsMap.put(uuid, armorStandsMap);
    }
    @Override
    public boolean minionClickedIsFromPlayer(UUID uuid, EntityMinion minion) {
        return uuid.equals(minion.getOwner());
    }

    @Override
    public void spawnMinion(EntityPlayer player, Location placeLocation, ItemStack head) {

        EntityMinion minion = EntityMinionNBT.getEntityMinion(head);
        int amount = EntityMinionNBT.getAmountMinion(head);
        EntityMinionNBT.setLocation(head, placeLocation);
        minion.setAmount(amount);
        EntityArmorStand armorStand = new EntityArmorStand(placeLocation, minion);
        armorStand.createEntityArmorStand();
        armorStand.setPlayerLocation(player.getLocation());
        armorStand.rotationBody(player.getLocation());
        armorStand.startLookingBlocks();
        addArmorStandsToPlayer(minion.getUUID(), armorStand);

    }

    @Override
    public void removeMinion(EntityPlayer player, EntityMinion minion) {
        EntityArmorStand entityArmorStand = entityArmorStandsMap.get(minion.getUUID());
        if(entityArmorStand == null){
            giveHeadToPlayerMinion(player, minion);
            player.sendMessage("{farm} <red>removed from world", map -> map.put("{farm}", minion.getDisplayName()));
            return;
        }
        entityArmorStand.cancelAnimateRightArm();
        entityArmorStand.stopRunnable(minion.getUUID());
        giveHeadToPlayerMinion(player, minion);
        entityArmorStandsMap.remove(minion.getUUID());
        player.sendMessage("{farm} <red>removed from world", map -> map.put("{farm}", minion.getDisplayName()));
    }


    @Override
    public void getItemFromMinion(EntityPlayer player, EntityMinion minion){
        EntityArmorStand armorStand = entityArmorStandsMap.get(minion.getUUID());
        if(armorStand == null){
            return;
        }
        player.addItemsOrDrop(ItemCreator.of(armorStand.getBlockFarm()).amount(armorStand.getAmount()).make());
        armorStand.setAmount(0);
    }


    @Override
    public void startEntityArmor(EntityMinion minion, ItemStack item, ArmorStand entity){
        if(entityArmorStandsMap.containsKey(minion.getUUID())) return;
        NBTDataHandler data = new NBTDataHandler(item, MINION_TAG);
         EntityArmorStand armorStand = new EntityArmorStand(data.getLocation(), minion, entity);
        armorStand.createHologram();
        armorStand.startLookingBlocks();
        addArmorStandsToPlayer(minion.getUUID(), armorStand);
    }

    @Override
    public void giveHeadToPlayer(EntityPlayer player, MinionType minionType) {
        EntityMinion minion = getEntityMinion(player.getItemHand());
        player.addItemsOrDrop(minion.getHead());
    }

    @Override
    public void giveHeadToPlayerMinion(EntityPlayer player, EntityMinion minion) {
        player.addItemsOrDrop(minion.getHead());
    }

    @Override
    public void newHeadToPlayer(EntityPlayer player, MinionType minionType){
        EntityMinion minion = createEntityMinion(player.getUniqueId(), UUID.randomUUID(), minionType);
        player.addItemsOrDrop(minion.getHead());
    }
}