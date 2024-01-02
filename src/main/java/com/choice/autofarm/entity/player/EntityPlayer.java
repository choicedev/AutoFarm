package com.choice.autofarm.entity.player;

import com.choice.autofarm.AutoFarm;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.PlayerUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityPlayer {

    private final Player player;

    public EntityPlayer(Player player) {
        this.player = player;
    }

    public UUID getUniqueId(){
        return this.player.getUniqueId();
    }

    public Location getLocation(){
        return this.player.getLocation();
    }

    public Inventory getInventory(){
        return this.player.getInventory();
    }

    public Map<Integer, ItemStack> removeItemInventory(ItemStack itemStack){
        return getInventory().removeItem(itemStack);
    }

    public boolean hasEmptyInventory(){
        return PlayerUtil.hasEmptyInventory(this.player);
    }

    public Map<Integer, ItemStack> addItems(Collection<ItemStack> items){
        return PlayerUtil.addItems(getInventory(), items.toArray(new ItemStack[items.size()]));
    }

    public Map<Integer, ItemStack> addItems(ItemStack... items){
        return PlayerUtil.addItems(getInventory(), items);
    }

    public boolean addItemsOrDrop(ItemStack... items){
        return PlayerUtil.addItemsOrDrop(this.player, items);
    }

    public void sendMessage(String message) {
        sendMessage(message, null);
    }

    public void sendMessage(String message, PlaceholdersFunction placeholders) {
        Map<String, String> map = new HashMap<>();
        if (placeholders != null) {
            placeholders.invoke(map);
        }

        AutoFarm.getAudiences().player(player).sendMessage(
                placeholders != null ? deserialize(message, map) : deserialize(message)
        );
    }

    private Component deserialize(String message, Map<String, String> placeholders) {
        return MiniMessage.miniMessage().deserialize(
                message,
                placeholders.entrySet().stream()
                        .map(entry -> Placeholder.parsed(entry.getKey(), entry.getValue()))
                        .toArray(TagResolver[]::new)
        );
    }

    private Component deserialize(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }


}
