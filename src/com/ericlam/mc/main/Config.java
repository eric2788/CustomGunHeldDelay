package com.ericlam.mc.main;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;

public class Config {
    public static String cdMSG, cdFinsih, notFinish;
    public static Sound sound, unableSound;
    private static Config config;
    private File itemFile;
    private FileConfiguration itemYml;
    private File configFile;
    private FileConfiguration configYml;
    private HashMap<String, Double> weaponDelay = new HashMap<>();

    private Config(Plugin plugin) {
        itemFile = new File(plugin.getDataFolder(), "weapons.yml");
        if (!itemFile.exists()) plugin.saveResource("weapons.yml", true);
        itemYml = YamlConfiguration.loadConfiguration(itemFile);
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) plugin.saveResource("config.yml", true);
        configYml = YamlConfiguration.loadConfiguration(configFile);
    }

    public static Config getInstance(Plugin plugin) {
        if (config == null) config = new Config(plugin);
        return config;
    }

    public HashMap<String, Double> getWeaponDelay() {
        return weaponDelay;
    }

    void loadItems() {
        cdMSG = ChatColor.translateAlternateColorCodes('&', configYml.getString("countdown-msg"));
        cdFinsih = ChatColor.translateAlternateColorCodes('&', configYml.getString("countdown-finish"));
        notFinish = configYml.getString("not-finish").isEmpty() ? null : ChatColor.translateAlternateColorCodes('&', configYml.getString("not-finish"));
        sound = configYml.getString("sound-effect").isEmpty() ? null : Sound.valueOf(configYml.getString("sound-effect"));
        unableSound = configYml.getString("unable-shoot").isEmpty() ? null : Sound.valueOf(configYml.getString("unable-shoot"));
        Set<String> key = itemYml.getKeys(false);
        for (String title : key) {
            double delay = itemYml.getDouble(title);
            double result;
            try {
                String delayformat = new DecimalFormat("#.#").format(delay);
                result = Double.parseDouble(delayformat);
            } catch (NumberFormatException e) {
                result = Math.rint(delay);
            }

            weaponDelay.put(title, result);
        }
    }

    public void reloadConfig() {
        configYml = YamlConfiguration.loadConfiguration(configFile);
        itemYml = YamlConfiguration.loadConfiguration(itemFile);
        loadItems();
    }
}
