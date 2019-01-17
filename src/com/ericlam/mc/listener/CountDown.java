package com.ericlam.mc.listener;

import com.ericlam.mc.main.Config;
import com.ericlam.mc.main.InvCSHDelay;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;

class CountDown {
    private Player player;
    private double count;
    private Plugin plugin;
    private BukkitTask runnable;

    CountDown(Player player, double count) {
        this.player = player;
        this.count = count;
        this.plugin = InvCSHDelay.plugin;
    }

    void start() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (count > 0.1) {
                    count -= 0.1;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Config.cdMSG.replace("<count>", str(count))));
                } else {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Config.cdFinsih));
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    boolean isFinished() {
        return runnable.isCancelled();
    }

    private String str(double count) {
        try {
            return new DecimalFormat().format(count);
        } catch (NumberFormatException e) {
            return Math.rint(count) + "";
        }
    }


}
