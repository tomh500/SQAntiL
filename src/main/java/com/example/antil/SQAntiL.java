package com.example.antil;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SQAntiL extends JavaPlugin {
    private final String[] FILTERED_WORDS = {"L", "FW"};
    private final String KICK_MESSAGE = ChatColor.RED + "You have been kicked for using inappropriate language.";

    @Override
    public void onEnable() {
        getLogger().info("SQAntiL has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SQAntiL has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        String message = String.join(" ", args);

        if (message.toLowerCase().contains("废物")) {
            player.kickPlayer(KICK_MESSAGE);
            return true;
        }

        for (String word : FILTERED_WORDS) {
            message = message.replace(word.toLowerCase(), "厉害");
        }

        switch (command.getName().toLowerCase()) {
            case "hello":
                player.sendMessage("Hello world!");
                break;
            case "foo":
                player.sendMessage("You typed foo!");
                break;
            default:
                this.getLogger().info("SQ on top1");
                player.sendMessage(ChatColor.RED + "Unknown command.");
                break;
        }

        return true;
    }
}
