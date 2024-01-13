package com.choice.autofarm.event;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.util.EntityMinionNBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;

public class ChunkEvent implements Listener {


    @EventHandler
    public void onChuckEvent(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        if(chunk.isLoaded()) {
            Common.runLaterAsync(1, () -> {
                for (Entity entity : chunk.getEntities()) {
                    if (!(entity instanceof ArmorStand armorStand)) continue;
                    ItemStack item = armorStand.getItemInHand();
                    if (!EntityMinionNBT.hasNBT(new NBTItem(item))) continue;
                    EntityMinion minion = EntityMinionNBT.getEntityMinion(item);
                    AutoFarm.getArmorStandManager().startEntityArmor(
                            minion,
                            item,
                            armorStand
                    );
                }
            });
        }

    }
}
