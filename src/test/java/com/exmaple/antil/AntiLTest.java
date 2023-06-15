package com.example.antil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

public class AntiLTest {
    private AntiL plugin;
    private CommandSender sender;
    private Player player;
    private Command command;

    @Before
    public void setUp() {
        plugin = new AntiL();
        sender = mock(CommandSender.class);
        player = mock(Player.class); // 创建虚拟玩家
        command = mock(Command.class);
    }

    @Test
    public void testOnCommand() {
        // 测试当输入匹配字符串时的情况
        String[] args1 = { "fl" };
        plugin.onCommand(sender, command, "", args1);
        verify(sender).sendMessage("厉害");

        String[] args2 = { "fwv" };
        plugin.onCommand(sender, command, "", args2);
        verify(sender).sendMessage("厉害");

        String[] args3 = { "L" };
        plugin.onCommand(sender, command, "", args3);
        verify(sender).sendMessage("厉害");

        String[] args4 = { "a.b" };
        plugin.onCommand(sender, command, "", args4);
        verify(sender).sendMessage("厉害");

        // 测试当输入的字符串包含敏感词汇时的情况
        String[] args5 = { "垃圾" };
        plugin.onCommand(player, command, "", args5);
        verify(player).kickPlayer("您的发言包含敏感词汇！");

        String[] args6 = { "这是一句废话" };
        plugin.onCommand(player, command, "", args6);
        verify(player).kickPlayer("您的发言包含敏感词汇！");

        // 测试当输入太快时的情况
        plugin.onCommand(player, command, "", args1);
        verify(player).sendMessage("厉害");

        plugin.onCommand(player, command, "", args2);
        verify(player).kickPlayer("您输入太快，请休息片刻！");

        // 测试当命令无法识别时的情况
        String[] args7 = {};
        plugin.onCommand(sender, command, "", args7);
        verify(sender).sendMessage("");
    }
}
