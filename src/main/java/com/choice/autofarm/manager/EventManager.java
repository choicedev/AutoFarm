package com.choice.autofarm.manager;

import com.choice.autofarm.Main;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    public void registerEvents(Listener listener) {
        PluginManager pm = Main.getInstance().getServer().getPluginManager();
        pm.registerEvents(listener, Main.getInstance());
    }
}
