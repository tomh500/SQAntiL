package com.example.antil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiL extends JavaPlugin implements Listener {
    // 存储玩家发送 L 和 fw 的次数
    private Map<UUID, Integer> lCounts = new HashMap<>();
    private Map<UUID, Integer> fwCounts = new HashMap<>();

    @Override
    public void onEnable() {
        // 注册事件监听器
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("AntiL plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("AntiL plugin disabled.");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // 获取发送信息的玩家
        Player player = event.getPlayer();

        // 判断发送的信息是否为 L 或 fw
        String message = event.getMessage();
        if (message.length() == 1 && message.equalsIgnoreCase("l")) {
            // 转换为厉害
            event.setMessage("厉害");
            // 记录次数
            int count = lCounts.getOrDefault(player.getUniqueId(), 0);
            lCounts.put(player.getUniqueId(), count + 1);
        } else if (message.equalsIgnoreCase("fw") || message.equalsIgnoreCase("fw ")) {
            // 转换为厉害
            event.setMessage("厉害");
            // 记录次数
            int count = fwCounts.getOrDefault(player.getUniqueId(), 0);
            fwCounts.put(player.getUniqueId(), count + 1);
        }

        // 判断是否需要踢出玩家
        if (exceededThreshold(player)) {
            // 发送警告信息
            player.sendMessage(ChatColor.RED + "您发送的 L 和 fw 过于频繁，被自动踢出服务器。");
            // 踢出玩家
            player.kickPlayer("您发送的 L 和 fw 过于频繁。");
        }
    }

    // 判断玩家是否超过发送 L 和 fw 的次数阈值
    private boolean exceededThreshold(Player player) {
        int lCount = lCounts.getOrDefault(player.getUniqueId(), 0);
        int fwCount = fwCounts.getOrDefault(player.getUniqueId(), 0);

        // 设置时间为 10 秒钟
        long timeThreshold = System.currentTimeMillis() - 10000;

        // 取出最近 10 秒钟的记录
        int lRecent = getRecentCount(lCounts, timeThreshold);
        int fwRecent = getRecentCount(fwCounts, timeThreshold);

        // 判断次数是否超过阈值
        return lCount > 5 || fwCount > 5 || lRecent > 10 || fwRecent > 10;
    }

    // 获取最近指定时间段内的发送 L 或 fw 的次数
    private int getRecentCount(Map<UUID, Integer> counts, long timeThreshold) {
        return (int) counts.values().stream()
                .filter(count -> count > 0 && count >= timeThreshold)
                .count();
    }
}
