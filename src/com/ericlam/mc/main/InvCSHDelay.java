package com.ericlam.mc.main;

import com.ericlam.mc.listener.WeaponsHeldListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class InvCSHDelay extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Config.getInstance(this).loadItems();
        this.getServer().getPluginManager().registerEvents(new WeaponsHeldListener(), this);
    }


}
