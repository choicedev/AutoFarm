package com.choice.autofarm.manager;

import com.choice.autofarm.AutoFarm;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    public void registerEvents(Listener listener) {
        PluginManager pm = AutoFarm.getInstance().getServer().getPluginManager();
        pm.registerEvents(listener, AutoFarm.getInstance());
    }
}
