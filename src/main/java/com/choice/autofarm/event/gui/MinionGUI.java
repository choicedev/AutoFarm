package com.choice.autofarm.event.gui;

import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class MinionGUI implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE).make());
    }
}
