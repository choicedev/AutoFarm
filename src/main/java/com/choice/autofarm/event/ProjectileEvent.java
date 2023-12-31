package com.choice.autofarm.event;

import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.EntityMinionNBT;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileEvent implements Listener {

    @EventHandler
    public void onEvent(ProjectileHitEvent event){
        if(!(event.getHitEntity() instanceof ArmorStand)) return;
        ArmorStand stand = (ArmorStand) event.getHitEntity();

        MinionType minion = EntityMinionNBT.getType(Material.DIAMOND_PICKAXE, stand.getItemInHand()).orElse(MinionType.NULL);
        if(minion == MinionType.NULL) return;
        event.setCancelled(true);
    }

}
