package com.choice.autofarm.event;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.data.NBTDataHandler;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.player.EntityPlayer;
import com.choice.autofarm.util.FarmConstants;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import static com.choice.autofarm.util.FarmConstants.MINION_TAG;

public class ItemEvent implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event){
        EntityPlayer player = new EntityPlayer((Player) event.getWhoClicked());
        ItemStack itemCrafted = event.getCurrentItem();
        if(itemCrafted == null) return;
        if(itemCrafted.getType() != Material.PLAYER_HEAD) return;

        NBTDataHandler data = new NBTDataHandler(itemCrafted, MINION_TAG);
        boolean isCustomCraft = data.getBoolean(FarmConstants.ENTITY_CRAFTED);
        if(!isCustomCraft) return;
        event.setCurrentItem(ItemCreator.of(CompMaterial.AIR).make());
        event.getRecipe();
        MinionType type = MinionType.valueOf(data.getString(FarmConstants.ENTITY_MINION_TYPE));
        AutoFarm.getArmorStandManager().newHeadToPlayer(player, type);
    }

}
