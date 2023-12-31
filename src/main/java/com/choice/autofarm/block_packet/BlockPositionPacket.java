package com.choice.autofarm.block_packet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.choice.autofarm.util.nms.NMSClass.getNMSClass;

public class BlockPositionPacket {

    @SuppressWarnings("CallToPrintStackTrace")
    private Object buildPacket(
            Location block,
            int status
    ) {
        try {
            Constructor<?> constructor = getNMSClass("BlockPosition").getConstructor(
                    int.class, int.class, int.class);
            Object blockPosition = constructor.newInstance(block.getBlockX(), block.getBlockY(), block.getBlockZ());
            Constructor<?> packetConstructor = getNMSClass("PacketPlayOutBlockBreakAnimation").getConstructor(
                    int.class, getNMSClass("BlockPosition"), int.class);

            return packetConstructor.newInstance(block.getBlockX(), blockPosition, status);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void sendPacket(Location block, int status) {
        try {
            Object packet = buildPacket(block, status);
            for (Player player : Bukkit.getOnlinePlayers()) {
                Object handle = player.getClass().getMethod("getHandle").invoke(player);
                Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
                playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
