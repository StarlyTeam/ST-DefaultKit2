package net.starly.defaultkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitCmdTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if (sender.hasPermission("starly.defaultkit." + config.getString("permissions.reload"))) completions.add("리로드");
            if (sender.hasPermission("starly.defaultkit." + config.getString("permissions.set"))) completions.add("설정");
            if (sender.hasPermission("starly.defaultkit." + config.getString("permissions.reset"))) completions.add("초기화");

            return completions;
        }

        if (args.length == 2) {
            if (List.of("초기화", "reset").contains(args[0].toLowerCase())) {
                if (sender.hasPermission("starly.defaultkit." + config.getString("permissions.reset"))) return null;
            }
        }

        return Collections.emptyList();
    }
}
