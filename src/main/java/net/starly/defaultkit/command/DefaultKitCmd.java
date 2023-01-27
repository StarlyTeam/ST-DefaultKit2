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
import java.util.Objects;
import java.util.stream.Collectors;

import static net.starly.defaultkit.DefaultKitMain.config;

public class DefaultKitCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(config.getMessage("messages.only_player"));
            return true;
        }
        Player player = (Player) sender;

        PlayerKitData data = new PlayerKitData(player);
        if (args.length == 0) {
            if (!player.hasPermission("starly.defaultkit.receive")) {
                player.sendMessage(config.getMessage("messages.no_permission"));
                return true;
            }
            if (data.isReceived()) {
                player.sendMessage(config.getMessage("messages.already_received"));
                return true;
            }
            if (36 - (int) Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).count() <
                    new DefaultKitData().getKit().size()) {
                player.sendMessage(config.getMessage("messages.inventory_no_space"));
                return true;
            }

            new DefaultKitData().giveKit(player);
            data.setReceived(true);
            player.sendMessage(config.getMessage("messages.kit_received.message"));
            if (config.getBoolean("messages.kit_received.title.enable")) {
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.title")),
                        ChatColor.translateAlternateColorCodes('&', config.getString("messages.kit_received.title.subtitle")),
                        config.getInt("messages.kit_received.title.fadein") * 20,
                        config.getInt("messages.kit_received.title.stay") * 20,
                        config.getInt("messages.kit_received.title.fadeout") * 20);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            return true;
        }

        switch (args[0].toLowerCase()) {
            case "리로드":
            case "reload": {
                if (!player.hasPermission("starly.defaultkit.reload")) {
                    player.sendMessage(config.getMessage("messages.no_permission"));
                    return true;
                }

                config.reloadConfig();
                player.sendMessage(config.getMessage("messages.reload"));
                return true;
            }

            case "설정":
            case "set": {
                if (!player.hasPermission("starly.defaultkit.set")) {
                    player.sendMessage(config.getMessage("messages.no_permission"));
                    return true;
                }
                if (args.length != 1) {
                    player.sendMessage(config.getMessage("messages.wrong_command"));
                    return true;
                }

                player.openInventory(config.getInventory("defaultkit"));
                KitEditorData.players.add(player);

                return true;
            }

            case "초기화":
            case "reset": {
                if (args.length == 1) {
                    if (!player.hasPermission("starly.defaultkit.reset.self")) {
                        player.sendMessage(config.getMessage("messages.no_permission"));
                        return true;
                    }

                    data.setReceived(false);
                    player.sendMessage(config.getMessage("messages.reset")
                            .replace("{target}", player.getDisplayName()));
                    return true;
                } else if (args.length == 2) {
                    if (!player.hasPermission("starly.defaultkit.reset.other")) {
                        player.sendMessage(config.getMessage("messages.no_permission"));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(config.getMessage("messages.no_player"));
                        return true;
                    }

                    new PlayerKitData(target).setReceived(false);
                    player.sendMessage(config.getMessage("messages.reset")
                            .replace("{target}", target.getDisplayName()));
                    return true;
                } else {
                    player.sendMessage(config.getMessage("messages.wrong_command"));
                    return true;
                }
            }

            default: {
                player.sendMessage(config.getMessage("messages.wrong_command"));
                return true;
            }
        }
    }
}
