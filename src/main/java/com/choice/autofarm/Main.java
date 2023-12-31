package com.choice.autofarm;

import co.aikar.commands.BukkitCommandManager;
import com.choice.autofarm.command.FarmCommand;
import com.choice.autofarm.event.InteractEvent;
import com.choice.autofarm.event.ProjectileEvent;
import com.choice.autofarm.manager.EventManager;
import com.choice.autofarm.manager.armorstand.MinionManager;
import com.choice.autofarm.manager.armorstand.IMinionManager;
import com.choice.autofarm.util.throwable.InstanceNotFound;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {


    private static JavaPlugin instance;
    private static MinionManager minionManager;
    private static BukkitAudiences audiences;

    @Override
    public void onEnable() {

        instance = this;
        audiences = BukkitAudiences.create(this);
        minionManager = new IMinionManager();
        new EventManager().registerEvents(new InteractEvent());
        new EventManager().registerEvents(new ProjectileEvent());

        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(new FarmCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }




    //Object
    public static JavaPlugin getInstance() {
        try{
            if(instance == null){
                throw new InstanceNotFound();
            }
            return instance;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static MinionManager getArmorStandManager(){
        try {
            if(minionManager == null) throw new RuntimeException("ArmorStandManager is null");
            return minionManager;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static BukkitAudiences getAudiences(){
        try {
            if(audiences == null) throw new RuntimeException("Audiences is null");
            return audiences;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
