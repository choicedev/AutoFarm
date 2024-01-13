package com.choice.autofarm.entity.minion;

import com.choice.autofarm.entity.minion.domain.MinionType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Map;
import java.util.UUID;

public interface EntityMinion {
    UUID getUUID();
    UUID getOwner();
    String getDisplayName();
    MinionType getMinionType();
    ItemStack getHead();
    ItemStack getItemHand();
    Map<String, ItemStack> getArmor();
    CompMaterial blockFarm();
    CompMaterial createBlock(Location location);
    void addAmount();
    int getAmount();
    void setAmount(int amount);

    boolean isValidBlocks(Block block);

    int getBreakDistance();

    boolean getAllowBreakVertical();

    Double getLevel();
    void updateLevel(Double level);

    String getStatus();
}
