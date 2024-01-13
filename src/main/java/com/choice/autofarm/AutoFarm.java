package com.choice.autofarm;

import co.aikar.commands.BukkitCommandManager;
import com.choice.autofarm.builder.farm.MinionsRecipe;
import com.choice.autofarm.command.FarmCommand;
import com.choice.autofarm.event.ChunkEvent;
import com.choice.autofarm.event.InteractEvent;
import com.choice.autofarm.event.ItemEvent;
import com.choice.autofarm.event.ProjectileEvent;
import com.choice.autofarm.manager.EventManager;
import com.choice.autofarm.manager.armorstand.IMinionManager;
import com.choice.autofarm.manager.armorstand.MinionManager;
import com.choice.autofarm.util.throwable.InstanceNotFound;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class AutoFarm extends SimplePlugin {


    private static AutoFarm instance;
    private static MinionManager minionManager;
    private static BukkitAudiences audiences;
    private static LegacyComponentSerializer legacyComponent;

    @Override
    protected void onPluginStart() {
        instance = (AutoFarm) SimplePlugin.getInstance();
        audiences = BukkitAudiences.create(this);
        legacyComponent = LegacyComponentSerializer.builder()
                .hexColors()
                .useUnusualXRepeatedCharacterHexFormat()
                .character(LegacyComponentSerializer.SECTION_CHAR)
                .character(LegacyComponentSerializer.AMPERSAND_CHAR)
                .character(LegacyComponentSerializer.HEX_CHAR)
                .build();
        minionManager = new IMinionManager();
        new EventManager().registerEvents(new InteractEvent());
        new EventManager().registerEvents(new ProjectileEvent());
        new EventManager().registerEvents(new ItemEvent());
        new EventManager().registerEvents(new ChunkEvent());
        MinionsRecipe.recipeFarmStone();
        MinionsRecipe.recipeFarmWheat();


        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(new FarmCommand());
    }

    //Object
    public static AutoFarm getInstance() {
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

    public static LegacyComponentSerializer getLegacyComponent(){
        try {
            if(legacyComponent == null) throw new RuntimeException("LegacyComponentSerializer is null");
            return legacyComponent;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
