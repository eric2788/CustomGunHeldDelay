package com.ericlam.mc.listener;

import com.ericlam.mc.main.Config;
import com.ericlam.mc.main.CustomGunHeldDelay;
import com.shampaggon.crackshot.CSUtility;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import me.DeeCaaD.CrackShotPlus.Events.WeaponHeldEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WeaponsHeldListener implements Listener {
    private final HashMap<String, Double> weaponDelay;
    private CountDownManager countDownManager;

    public WeaponsHeldListener() {
        weaponDelay = Config.getInstance(CustomGunHeldDelay.plugin).getWeaponDelay();
        countDownManager = CountDownManager.getInstance();
    }

    @EventHandler
    public void onWeaponsHeld(WeaponHeldEvent e) {
        String title = e.getWeaponTitle();
        Player player = e.getPlayer();
        if (!weaponDelay.containsKey(title)) {
            if (!countDownManager.isFinsihed(player)) countDownManager.cancelPlayer(player);
            return;
        }
        double delay = weaponDelay.get(title);
        countDownManager.addCountDown(player, delay);
        if (Config.sound != null) player.playSound(player.getLocation(), Config.sound, 1, 1);
    }

    @EventHandler
    public void onWeaponPreShoot(WeaponPrepareShootEvent e) {
        Player player = e.getPlayer();
        if (!countDownManager.isFinsihed(player)) {
            e.setCancelled(true);
            player.sendMessage(Config.notFinish);
            if (Config.unableSound != null) player.playSound(player.getLocation(), Config.unableSound, 1, 1);
        }
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItem(e.getNewSlot());
        if (new CSUtility().getWeaponTitle(item) != null) return;
        countDownManager.cancelPlayer(player);
    }
}
