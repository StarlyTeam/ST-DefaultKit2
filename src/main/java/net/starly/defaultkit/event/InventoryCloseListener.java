package net.starly.defaultkit.event;

import net.starly.defaultkit.data.DefaultKitData;
import net.starly.defaultkit.data.KitEditorData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.Objects;

import static net.starly.defaultkit.DefaultKitMain.config;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!KitEditorData.players.contains(player)) return;
        KitEditorData.players.remove(player);

        if (Arrays.stream(event.getInventory().getContents()).noneMatch(Objects::nonNull)) {
            player.sendMessage(config.getMessage("messages.kit_cannot_empty"));
            return;
        }

        new DefaultKitData().setKit(Arrays.asList(event.getInventory().getContents()));
        player.sendMessage(config.getMessage("messages.kit_set"));
    }
}
