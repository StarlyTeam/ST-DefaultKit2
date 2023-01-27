package net.starly.defaultkit.event;

import net.starly.defaultkit.data.DefaultKitData;
import net.starly.defaultkit.data.KitEditorData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.starly.defaultkit.DefaultKitMain.config;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (!KitEditorData.players.contains(p)) return;
        KitEditorData.players.remove(p);

        if (Arrays.stream(e.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList()).size() == 0) {
            p.sendMessage(config.getMessage("messages.kit_cannot_empty"));
            return;
        }

        new DefaultKitData().setKit(e.getInventory());
        p.sendMessage(config.getMessage("messages.kit_set"));
    }
}
