package com.choice.autofarm.entity.minion;

import com.choice.autofarm.entity.minion.domain.MinionType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface EntityMinion {

    UUID getUUID();
    UUID getOwner();
    String getDisplayName();
    String getTexture();
    MinionType getMinionType();

    ItemStack getHead();

    void addAmount();

    int getAmount();

    void setAmount(int i);
}
