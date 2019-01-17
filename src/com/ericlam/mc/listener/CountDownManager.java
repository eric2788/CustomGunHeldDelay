package com.ericlam.mc.listener;

import com.ericlam.mc.main.InvCSHDelay;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

class CountDownManager {
    private static CountDownManager countDownManager;
    private HashMap<Player, CountDown> cooldownMap = new HashMap<>();
    private Plugin plugin;

    private CountDownManager() {
        plugin = InvCSHDelay.plugin;
    }

    static CountDownManager getInstance() {
        if (countDownManager == null) countDownManager = new CountDownManager();
        return countDownManager;
    }

    void addCountDown(Player player, double count) {
        cooldownMap.put(player, new CountDown(player, count));
        cooldownMap.get(player).start();
    }

    boolean isFinsihed(Player player) {
        if (!cooldownMap.containsKey(player)) return true;
        return cooldownMap.get(player).isFinished();
    }
}
