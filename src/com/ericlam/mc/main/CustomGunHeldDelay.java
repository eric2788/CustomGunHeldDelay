package com.ericlam.mc.main;

import com.ericlam.mc.listener.WeaponsHeldListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomGunHeldDelay extends JavaPlugin implements CommandExecutor {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Config.getInstance(this).loadItems();
        this.getServer().getPluginManager().registerEvents(new WeaponsHeldListener(), this);
        this.getLogger().info("插件已啟用");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cshreload")) {
            if (sender.hasPermission("csh.reload")) {
                Config.getInstance(plugin).reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Reload Complete.");
            } else {
                sender.sendMessage(ChatColor.RED + "No permission");
            }
        }
        return true;
    }
}
