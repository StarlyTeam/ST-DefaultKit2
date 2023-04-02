package net.starly.defaultkit;

import net.starly.core.bstats.Metrics;
import net.starly.core.data.Config;
import net.starly.defaultkit.command.tabcomplete.DefaultKitTab;
import net.starly.defaultkit.command.DefaultKitCmd;
import net.starly.defaultkit.listener.InventoryCloseListener;
import net.starly.defaultkit.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultKitMain extends JavaPlugin {
    private static JavaPlugin plugin;
    public static Config config;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (!isPluginEnabled("net.starly.core.StarlyCore")) {
            getServer().getLogger().warning("[" + getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            getServer().getLogger().warning("[" + getName() + "] 다운로드 링크 : &fhttp://starly.kr/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        plugin = this;

        new Metrics(this, 17361);


        // CONFIG
        config = new Config("config", plugin);
        config.loadDefaultConfig();
        config.setPrefix("messages.prefix");


        // COMMAND
        getServer().getPluginCommand("default-kit").setExecutor(new DefaultKitCmd());
        getServer().getPluginCommand("default-kit").setTabCompleter(new DefaultKitTab());


        // EVENT
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), plugin);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    private boolean isPluginEnabled(String path) {
        try {
            Class.forName(path);
            return true;
        } catch (ClassNotFoundException ignored) {
        } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    }
}
