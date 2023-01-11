package net.starly.defaultkit.event;

import net.starly.defaultkit.data.DefaultKitData;
import net.starly.defaultkit.data.KitEditorData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import static net.starly.defaultkit.DefaultKitMain.config;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if (!KitEditorData.players.contains(p)) return;
        if (!p.hasPermission("starly.defaultkit." + config.getString("permissions.set"))) {
            p.sendMessage(config.getMessage("messages.no_permission"));
            return;
        }


        new DefaultKitData().setKit(e.getInventory());
        p.sendMessage(config.getMessage("messages.kit_set"));

        KitEditorData.players.remove(p);
    }
}
