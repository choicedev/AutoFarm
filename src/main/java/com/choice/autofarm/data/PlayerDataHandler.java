package com.choice.autofarm.data;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataHandler {

    private final Plugin plugin;
    private final Map<String, NamespacedKey> keys;

    public PlayerDataHandler(Plugin plugin) {
        this.plugin = plugin;
        this.keys = new HashMap<>();
    }

    public void registerKey(String keyName, PersistentDataType<?, ?> dataType) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        keys.put(keyName, key);
    }

    public String getString(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.STRING);
    }

    public void setString(Player player, String keyName, String value) {
        set(player, keyName, PersistentDataType.STRING, value);
    }

    public boolean getBoolean(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.INTEGER) == 1;
    }

    public void setBoolean(Player player, String keyName, boolean value) {
        set(player, keyName, PersistentDataType.INTEGER, value ? 1 : 0);
    }

    public double getDouble(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.DOUBLE);
    }

    public void setDouble(Player player, String keyName, double value) {
        set(player, keyName, PersistentDataType.DOUBLE, value);
    }

    public int getInt(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.INTEGER);
    }

    public void setInt(Player player, String keyName, int value) {
        set(player, keyName, PersistentDataType.INTEGER, value);
    }

    private <T, Z> T get(Player player, String keyName, PersistentDataType<Z, T> dataType) {
        NamespacedKey key = keys.get(keyName);
        if (key != null) {
            PersistentDataContainer container = player.getPersistentDataContainer();
            return container.get(key, dataType);
        }
        return null;
    }

    private <T, Z> void set(Player player, String keyName, PersistentDataType<Z, T> dataType, T value) {
        NamespacedKey key = keys.get(keyName);
        if (key != null) {
            PersistentDataContainer container = player.getPersistentDataContainer();
            container.set(key, dataType, value);
        }
    }
}


