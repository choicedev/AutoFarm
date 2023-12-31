package com.choice.autofarm.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemStackBuilder {

    private final ItemStack itemStack;
    private final ItemMeta meta;
    private final Map<Enchantment, Integer> enchantments;
    private final List<ItemFlag> itemFlags;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.meta = itemStack.getItemMeta();
        this.enchantments = new HashMap<>();
        this.itemFlags = new ArrayList<>();
    }

    public ItemStackBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.meta = itemStack.getItemMeta();
        this.enchantments = new HashMap<>();
        this.itemFlags = new ArrayList<>();
    }

    public ItemStackBuilder displayName(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemStackBuilder setAmount(int amount){
        itemStack.setAmount(amount);
        return this;
    }

    public int getAmount(){
        return itemStack.getAmount();
    }

    public ItemStackBuilder lore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        List<String> loreList = new ArrayList<>();
        Collections.addAll(loreList, lore);
        return lore(loreList);
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemStackBuilder addItemFlags(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            itemFlags.add(flag);
        }
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantments(enchantments);

        if (!itemFlags.isEmpty()) {
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        }

        return itemStack;
    }
}

