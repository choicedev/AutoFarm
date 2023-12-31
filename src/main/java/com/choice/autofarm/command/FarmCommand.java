package com.choice.autofarm.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.choice.autofarm.Main;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.minion.EntityMinion;
import com.choice.autofarm.factory.MinionFactory;
import com.choice.autofarm.manager.armorstand.MinionManager;
import com.choice.autofarm.entity.player.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

@CommandAlias("farm")
public class FarmCommand extends BaseCommand {


    @Subcommand("stone")
    public static void onGiveFarmStone(Player player){
        EntityPlayer entityPlayer = new EntityPlayer(player);

        Main.getArmorStandManager().giveHeadToPlayer(player.getPlayer(), MinionType.STONE);
        entityPlayer.sendMessage("<green>New farm in added");

    }
}
