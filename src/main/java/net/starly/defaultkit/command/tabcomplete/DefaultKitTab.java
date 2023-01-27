package net.starly.defaultkit.command.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if (sender.hasPermission("starly.defaultkit.reload")) completions.add("리로드");
            if (sender.hasPermission("starly.defaultkit.set")) completions.add("설정");
            if (sender.hasPermission("starly.defaultkit.reset")) completions.add("초기화");

            return completions;
        }

        if (args.length == 2) {
            if (Arrays.asList("초기화", "reset").contains(args[0].toLowerCase())) {
                if (sender.hasPermission("starly.defaultkit.reset")) return null;
            }
        }

        return Collections.emptyList();
    }
}
