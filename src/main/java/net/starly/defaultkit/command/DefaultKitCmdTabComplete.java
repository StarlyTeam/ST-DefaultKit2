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
            List<String> completion = new ArrayList<>();
            if (sender.hasPermission("starly.defaultkit." + config.getString("permissions.reload"))) completion.add("리로드");
            if (sender.hasPermission("starly.defaultkit." + config.getString("permissions.set"))) completion.add("설정");

            return completion;
        }

        return Collections.emptyList();
    }
}
