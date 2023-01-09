package net.starly.defaultkit.data;

import net.starly.core.data.Config;
import net.starly.defaultkit.DefaultKitMain;
import org.bukkit.entity.Player;

public class PlayerDefaultKitData {
    private final Config config;

    public PlayerDefaultKitData(Player player) {
        this.config = new Config("data/" + player.getUniqueId(), DefaultKitMain.getPlugin());

        if (config.getObject("received") == null) config.setBoolean("received", false);
    }

    public void setReceived(boolean received) {
        config.setBoolean("received", received);
    }

    public boolean isReceived() {
        return config.getBoolean("received");
    }
}
