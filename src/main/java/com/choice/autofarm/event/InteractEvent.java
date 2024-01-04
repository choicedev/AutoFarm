package com.choice.autofarm.event;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.player.EntityPlayer;
import com.choice.autofarm.manager.armorstand.MinionManager;
import com.choice.autofarm.util.EntityMinionNBT;
import com.choice.autofarm.util.FarmConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class InteractEvent implements Listener {
    MinionManager manager = AutoFarm.getArmorStandManager();

    @EventHandler
    public void onInteractWithArmorStand(PlayerInteractEvent event) {
        if (!isValidInteraction(event)) return;

        EntityPlayer player = new EntityPlayer(event.getPlayer());
        Location placeLocation = event.getClickedBlock().getLocation();
        MinionType minionType = EntityMinionNBT.getType(event.getItem(), CompMaterial.PLAYER_HEAD);
        if (minionType == MinionType.NULL) return;

        event.setCancelled(true);
        removePlayerItem(player, event.getItem());

        manager.spawnMinion(player, placeLocation, getMinionUuid(event.getItem()), minionType);
    }

    @EventHandler
    public void onInteractRemoveEntity(PlayerInteractAtEntityEvent event) {
        if (!isValidArmorStandEntity(event.getRightClicked())) return;
        EntityPlayer player = new EntityPlayer(event.getPlayer());

        ArmorStand entity = (ArmorStand) event.getRightClicked();
        String entityUUID = EntityMinionNBT.getMinionUUID(entity.getItemInHand());
        EntityMinion minion = manager.getEntityMinionByUUID(player.getUniqueId(), entityUUID);
        if (minion == null) return;

        event.setCancelled(true);
        manager.getItemFromMinion(player, minion);
    }

    @EventHandler
    public void onAttackMinion(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof ArmorStand)) return;

        EntityPlayer player = new EntityPlayer((Player) event.getDamager());
        ArmorStand entity = (ArmorStand) event.getEntity();
        String entityUUID = EntityMinionNBT.getMinionUUID(entity.getItemInHand());
        EntityMinion minion = manager.getEntityMinionByUUID(player.getUniqueId(), entityUUID);
        if (minion == null) return;
        event.setCancelled(true);
        if(!manager.minionClickedIsFromPlayer(player.getUniqueId())) return;

        manager.removeMinion(player, minion);

    }

    private boolean isValidInteraction(PlayerInteractEvent event) {
        return event.getClickedBlock() != null && event.hasItem() && event.hasBlock() && event.getItem().getType() == Material.PLAYER_HEAD && event.getClickedBlock().getType() != Material.AIR && event.getAction() == Action.RIGHT_CLICK_BLOCK;
    }

    private String getMinionUuid(ItemStack item) {
        return EntityMinionNBT.getStringInfo(CompMaterial.DIAMOND_PICKAXE, item, FarmConstants.ENTITY_MINION_UUID);
    }

    private boolean isValidArmorStandEntity(Entity entity) {
        return entity instanceof ArmorStand;
    }

    private void removePlayerItem(EntityPlayer player, ItemStack item) {
        player.removeItemInventory(item);
    }
}
