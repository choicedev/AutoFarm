package com.choice.autofarm.event.gui;

import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import org.bukkit.entity.Player;

public class MinionGUI implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        InventoryProvider.super.init(player, contents);
    }
}
