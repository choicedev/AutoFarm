package com.choice.autofarm.event;

import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.EntityMinionNBT;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.mineacademy.fo.remain.CompMaterial;

public class ProjectileEvent implements Listener {

    @EventHandler
    public void onEvent(ProjectileHitEvent event){
        if(!(event.getHitEntity() instanceof ArmorStand stand)) return;

        MinionType minion = EntityMinionNBT.getType(stand.getItemInHand(), CompMaterial.DIAMOND_PICKAXE);
        if(minion == MinionType.NULL) return;
        event.setCancelled(true);
    }

}
