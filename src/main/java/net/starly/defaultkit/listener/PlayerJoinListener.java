package net.starly.defaultkit.listener;

import net.starly.defaultkit.data.DefaultKitData;
import net.starly.defaultkit.data.PlayerKitData;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.Objects;

import static net.starly.defaultkit.DefaultKitMain.config;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent Event) {
        if (!config.getBoolean("settings.enable_auto_kit")) return;

        Player player = Event.getPlayer();
        if (!player.hasPermission("starly.defaultkit.receive")) return;

        PlayerKitData data = new PlayerKitData(player);
        if (data.isReceived()) return;
        if (36 - (int) Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).count() <
                new DefaultKitData().getKit().size()) {
            player.sendMessage(config.getMessage("messages.inventory_no_space"));
            return;
        }

        new DefaultKitData().giveKit(player);
        data.setReceived(true);
        player.sendMessage(config.getMessage("messages.kit_received.message"));
        if (config.getBoolean("messages.kit_received.title.enable")) {
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.title")),
                    ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.subtitle")),
                    config.getInt("messages.kit_received.title.fadein") * 20,
                    config.getInt("messages.kit_received.title.stay") * 20,
                    config.getInt("messages.kit_received.title.fadeout") * 20);
        }
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
}
