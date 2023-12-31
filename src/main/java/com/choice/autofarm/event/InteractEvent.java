package com.choice.autofarm.event;

import com.choice.autofarm.Main;
import com.choice.autofarm.builder.ItemStackBuilder;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.player.EntityPlayer;
import com.choice.autofarm.util.MinionConstants;
import com.choice.autofarm.util.SkullItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {


    @EventHandler
    public void onInteractWithAmorStand(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) return;
        if (!event.hasItem()) return;
        if (!event.hasBlock()) return;
        if (event.getItem().getType() != Material.PLAYER_HEAD) return;
        if (event.getClickedBlock().getType() == Material.AIR) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Location placeLocation = event.getClickedBlock().getLocation();

        MinionType minionType = SkullItem.getEntityMinionType(Material.PLAYER_HEAD, event.getItem()).orElse(MinionType.NULL);
        if (minionType == MinionType.NULL) return;

        String entityUuid = SkullItem.getEntityMinionInfo(Material.PLAYER_HEAD, event.getItem(), MinionConstants.ENTITY_MINION_UUID).orElse("");
        event.setCancelled(true);
        player.getInventory().removeItem(event.getItem());
        Main.getArmorStandManager().spawnMinion(player, placeLocation, entityUuid, minionType);
    }


    @EventHandler
    public void onInteractRemoveEntity(PlayerInteractAtEntityEvent event) {
        EntityPlayer player = new EntityPlayer(event.getPlayer());


        if(!(event.getRightClicked() instanceof ArmorStand)) return;
        ArmorStand entity = (ArmorStand) event.getRightClicked();
        MinionType minionType = SkullItem.getEntityMinionType(Material.DIAMOND_PICKAXE, entity.getItemInHand()).orElse(MinionType.NULL);

        if(minionType == MinionType.NULL) return;

        String minionUuid = SkullItem.getEntityMinionInfo(Material.DIAMOND_PICKAXE, entity.getItemInHand(), MinionConstants.ENTITY_MINION_UUID).orElse("");
        EntityMinion minion = Main.getArmorStandManager().getEntityMinionByUuid(player.getPlayer().getUniqueId(), minionUuid);

        if(minionType == MinionType.STONE){
            player.getPlayer().getInventory().addItem(new ItemStackBuilder(Material.COBBLESTONE).setAmount(minion.getAmount()).build());
        }
        minion.setAmount(0);
        event.setCancelled(true);

    }

    @EventHandler
    public void onAttackMinion(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof  Player)) return;
        if(!(event.getEntity() instanceof ArmorStand)) return;

        EntityPlayer player = new EntityPlayer((Player) event.getDamager());
        ArmorStand entity = (ArmorStand) event.getEntity();
        MinionType minionType = SkullItem.getEntityMinionType(Material.DIAMOND_PICKAXE, entity.getItemInHand()).orElse(MinionType.NULL);

        if(minionType == MinionType.NULL) return;

        event.setCancelled(true);
        String minionName = SkullItem.getMinionEntityName(entity).orElse("");
        String minionUuid = SkullItem.getEntityMinionInfo(Material.DIAMOND_PICKAXE, entity.getItemInHand(), MinionConstants.ENTITY_MINION_UUID).orElse("");
        EntityMinion minion = Main.getArmorStandManager().getEntityMinionByUuid(player.getPlayer().getUniqueId(), minionUuid);

        if(minionType == MinionType.STONE){
            player.getPlayer().getInventory().addItem(new ItemStackBuilder(Material.COBBLESTONE).setAmount(minion.getAmount()).build());
        }
        entity.remove();
        Main.getArmorStandManager().deleteArmor(player.getPlayer(), minionUuid, minionType);
        player.sendMessage("<gold><farm> removed from world", map -> {
            map.put("farm", minionName);
        });
    }


}
