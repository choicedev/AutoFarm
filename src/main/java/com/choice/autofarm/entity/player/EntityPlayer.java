package com.choice.autofarm.entity.player;

import com.choice.autofarm.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class EntityPlayer {

    private final Player player;

    public EntityPlayer(Player player) {
        this.player = player;
    }

    public void sendMessage(String message) {
        sendMessage(message, null);
    }

    public void sendMessage(String message, PlaceholdersFunction placeholders) {
        Map<String, String> map = new HashMap<>();
        if (placeholders != null) {
            placeholders.invoke(map);
        }

        Main.getAudiences().player(player).sendMessage(
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

    public Player getPlayer() {
        return player;
    }


}
