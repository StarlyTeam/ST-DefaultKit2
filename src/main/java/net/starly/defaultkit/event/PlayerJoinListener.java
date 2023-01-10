package net.starly.defaultkit.event;

import net.starly.defaultkit.data.DefaultKitData;
import net.starly.defaultkit.data.PlayerDefaultKitData;
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
    public void onJoin(PlayerJoinEvent e) {
        if (!config.getBoolean("settings.enable_auto_kit")) return;

        Player p = e.getPlayer();
        if (!p.hasPermission("starly.defaultkit." + config.getString("settings.auto_kit_permission"))) return;

        PlayerDefaultKitData data = new PlayerDefaultKitData(p);
        if (data.isReceived()) return;
        if (Arrays.stream(p.getInventory().getContents()).filter(Objects::nonNull).toList().size() != 0) {
            p.sendMessage(config.getMessage("messages.inventory_not_empty"));
            return;
        }

        new DefaultKitData().giveKit(p);
        data.setReceived(true);
        p.sendMessage(config.getMessage("messages.kit_received"));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
}
