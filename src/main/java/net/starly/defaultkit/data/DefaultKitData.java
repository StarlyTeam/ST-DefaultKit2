package net.starly.defaultkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitData {
    public void giveKit(Player p) {
        p.getInventory().setContents(config.getInventory("defaultkit").getContents());
    }

    public void setKit(Inventory inv) {
        config.setInventory("defaultkit", inv, "기본템 설정 | ST-DefaultKit");
    }
}
