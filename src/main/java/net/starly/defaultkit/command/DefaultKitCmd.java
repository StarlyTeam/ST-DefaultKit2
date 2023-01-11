package net.starly.defaultkit.command;

import net.starly.defaultkit.data.DefaultKitData;
import net.starly.defaultkit.data.KitEditorData;
import net.starly.defaultkit.data.PlayerKitData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(config.getMessage("messages.only_player"));
            return true;
        }

        PlayerKitData data = new PlayerKitData(p);
        if (args.length == 0) {
            if (!p.hasPermission("starly.defaultkit." + config.getString("permissions.receive_kit"))) {
                p.sendMessage(config.getMessage("messages.no_permission"));
                return true;
            }
            if (data.isReceived()) {
                p.sendMessage(config.getMessage("messages.already_received"));
                return true;
            }
            if (36 - Arrays.stream(p.getInventory().getContents()).filter(Objects::nonNull).toList().size() <
                    new DefaultKitData().getKit().size()) {
                p.sendMessage(config.getMessage("messages.inventory_no_space"));
                return true;
            }

            new DefaultKitData().giveKit(p);
            data.setReceived(true);
            p.sendMessage(config.getMessage("messages.kit_received.message"));
            if (config.getBoolean("messages.kit_received.title.enable")) {
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.title")),
                        ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.subtitle")),
                        config.getInt("messages.kit_received.title.fadein") * 20,
                        config.getInt("messages.kit_received.title.stay") * 20,
                        config.getInt("messages.kit_received.title.fadeout") * 20);
            }
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            return true;
        }

        switch (args[0].toLowerCase()) {
            case "리로드", "reload", "rl" -> {
                if (!p.hasPermission("starly.defaultkit." + config.getString("permissions.reload"))) {
                    p.sendMessage(config.getMessage("messages.no_permission"));
                    return true;
                }

                config.reloadConfig();
                p.sendMessage(config.getMessage("messages.reload"));
                return true;
            }

            case "설정", "set" -> {
                if (!p.hasPermission("starly.defaultkit." + config.getString("permissions.set"))) {
                    p.sendMessage(config.getMessage("messages.no_permission"));
                    return true;
                }
                if (args.length != 1) {
                    p.sendMessage(config.getMessage("messages.wrong_command"));
                    return true;
                }

                p.openInventory(config.getInventory("defaultkit"));
                KitEditorData.players.add(p);

                return true;
            }

            case "초기화", "reset" -> {
                if (!p.hasPermission("starly.defaultkit." + config.getString("permissions.reset"))) {
                    p.sendMessage(config.getMessage("messages.no_permission"));
                    return true;
                }
                if (args.length == 1) {
                    data.setReceived(false);
                    p.sendMessage(config.getMessage("messages.reset", Map.of("{target}", p.getDisplayName())));
                    return true;
                } else if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(config.getMessage("messages.no_player"));
                        return true;
                    }

                    new PlayerKitData(target).setReceived(false);
                    p.sendMessage(config.getMessage("messages.reset", Map.of("{target}", target.getDisplayName())));
                    return true;
                } else {
                    p.sendMessage(config.getMessage("messages.wrong_command"));
                    return true;
                }
            }

            default -> {
                p.sendMessage(config.getMessage("messages.wrong_command"));
                return true;
            }
        }
    }
}
