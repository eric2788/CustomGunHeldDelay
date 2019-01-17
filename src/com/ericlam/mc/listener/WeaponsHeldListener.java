package com.ericlam.mc.listener;

import com.ericlam.mc.main.Config;
import com.ericlam.mc.main.InvCSHDelay;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import me.DeeCaaD.CrackShotPlus.Events.WeaponHeldEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class WeaponsHeldListener implements Listener {
    private final HashMap<String, Double> weaponDelay;
    private CountDownManager countDownManager;

    public WeaponsHeldListener() {
        weaponDelay = Config.getInstance(InvCSHDelay.plugin).getWeaponDelay();
        countDownManager = CountDownManager.getInstance();
    }

    @EventHandler
    public void onWeaponsHeld(WeaponHeldEvent e) {
        String title = e.getWeaponTitle();
        Player player = e.getPlayer();
        if (!weaponDelay.containsKey(title)) return;
        double delay = weaponDelay.get(title);
        countDownManager.addCountDown(player, delay);
    }

    @EventHandler
    public void onWeaponPreShoot(WeaponPrepareShootEvent e) {
        Player player = e.getPlayer();
        if (!countDownManager.isFinsihed(player)) {
            e.setCancelled(true);
            player.sendMessage(Config.notFinish);
        }
    }
}
