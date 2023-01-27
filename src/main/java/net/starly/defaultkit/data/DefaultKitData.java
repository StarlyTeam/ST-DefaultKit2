package net.starly.defaultkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitData {
    public void giveKit(Player p) {
        p.getInventory().addItem(Arrays.stream(config.getInventory("defaultkit").getContents()).filter(Objects::nonNull).toArray(ItemStack[]::new));
    }

    public List<ItemStack> getKit() {
        return Arrays.stream(config.getInventory("defaultkit").getContents()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void setKit(Inventory inv) {
        config.setInventory("defaultkit", inv, "기본템 설정 | ST-DefaultKit");
    }
}
