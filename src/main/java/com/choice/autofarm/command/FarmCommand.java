package com.choice.autofarm.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.entity.player.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.DISTANCE;

@CommandAlias("farm")
public class FarmCommand extends BaseCommand {


    @Subcommand("stone")
    public static void onGiveFarmStone(Player player){
        EntityPlayer entityPlayer = new EntityPlayer(player);

        AutoFarm.getArmorStandManager().giveHeadToPlayer(entityPlayer, MinionType.STONE);
        entityPlayer.sendMessage("<green>New farm in added");

    }

    @Subcommand("wheat")
    public static void onGiveFarmWheat(Player player){
        EntityPlayer entityPlayer = new EntityPlayer(player);

        AutoFarm.getArmorStandManager().giveHeadToPlayer(entityPlayer, MinionType.WHEAT);
        Bukkit.broadcastMessage(""+ DISTANCE);
        entityPlayer.sendMessage("<green>New farm in added");

    }
}
