package net.starly.defaultkit.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitData {
    public void giveKit(Player p) {
        getKit().forEach(p.getInventory()::addItem);
    }

    public List<ItemStack> getKit() {
        if (config.getConfig().getList("defaultkit") == null) {
            config.setObjectList("defaultkit", new ArrayList<>());
        }

        return config.getObjectList("defaultkit").stream().map(s -> (ItemStack) s).collect(Collectors.toList());
    }

    public void setKit(List<ItemStack> items) {
        config.setObjectList("defaultkit", items.stream().filter(Objects::nonNull).map(s -> (Object) s).collect(Collectors.toList()));
    }
}
