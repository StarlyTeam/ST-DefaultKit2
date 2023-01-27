package net.starly.defaultkit.event;

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
import java.util.stream.Collectors;

import static net.starly.defaultkit.DefaultKitMain.config;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!config.getBoolean("settings.enable_auto_kit")) return;

        Player p = e.getPlayer();
        if (!p.hasPermission("starly.defaultkit." + config.getString("settings.auto_kit_permission"))) return;

        PlayerKitData data = new PlayerKitData(p);
        if (data.isReceived()) return;
        if (36 - (int) Arrays.stream(p.getInventory().getContents()).filter(Objects::nonNull).count() <
                new DefaultKitData().getKit().size()) {
            p.sendMessage(config.getMessage("messages.inventory_no_space"));
            return;
        }

        new DefaultKitData().giveKit(p);
        data.setReceived(true);
        p.sendMessage(config.getMessage("messages.kit_received.message"));
        if (config.getBoolean("messages.kit_received.title.enable")) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.title")),
                    ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.subtitle")),
                    config.getInt("messages.kit_received.title.fadein") * 20,
                    config.getInt("messages.kit_received.title.stay") * 20,
                    config.getInt("messages.kit_received.title.fadeout") * 20);
        }
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
}
