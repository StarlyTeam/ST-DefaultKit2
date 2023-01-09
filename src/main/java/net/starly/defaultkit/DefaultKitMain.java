package net.starly.defaultkit;

import net.starly.core.bstats.Metrics;
import net.starly.core.data.Config;
import net.starly.defaultkit.command.DefaultKitCmdTabComplete;
import net.starly.defaultkit.command.DefaultKitCommand;
import net.starly.defaultkit.event.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultKitMain extends JavaPlugin {
    private static JavaPlugin plugin;
    public static Config config;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (Bukkit.getPluginManager().getPlugin("ST-Core") == null) {
            Bukkit.getLogger().warning("[" + plugin.getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            Bukkit.getLogger().warning("[" + plugin.getName() + "] 다운로드 링크 : &fhttps://discord.gg/TF8jqSJjCG");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }


        plugin = this;

        new Metrics(this, 17361);


        // CONFIG
        config = new Config("config", plugin);
        config.loadDefaultConfig();
        config.setPrefix("messages.prefix");


        // COMMAND
        Bukkit.getPluginCommand("defaultkit").setExecutor(new DefaultKitCommand());
        Bukkit.getPluginCommand("defaultkit").setTabCompleter(new DefaultKitCmdTabComplete());


        // EVENT
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
