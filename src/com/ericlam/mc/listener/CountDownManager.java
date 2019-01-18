package com.ericlam.mc.listener;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CountDownManager {
    private static CountDownManager countDownManager;
    private HashMap<Player, CountDown> cooldownMap = new HashMap<>();

    public static CountDownManager getInstance() {
        if (countDownManager == null) countDownManager = new CountDownManager();
        return countDownManager;
    }

    public void addCountDown(Player player, double count) {
        if (cooldownMap.containsKey(player)) {
            cooldownMap.get(player).setCancel();
            cooldownMap.remove(player);
        }
        cooldownMap.put(player, new CountDown(player, count));
        cooldownMap.get(player).start();
    }

    public boolean isFinsihed(Player player) {
        if (!cooldownMap.containsKey(player)) return true;
        return cooldownMap.get(player).isFinished();
    }

    public void cancelPlayer(Player player) {
        if (!cooldownMap.containsKey(player)) return;
        cooldownMap.get(player).setCancel();
    }
}
