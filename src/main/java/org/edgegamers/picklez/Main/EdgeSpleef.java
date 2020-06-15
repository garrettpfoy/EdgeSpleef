package org.edgegamers.picklez.Main;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.edgegamers.picklez.Commands.*;
import org.edgegamers.picklez.Hooks.SpleefPlaceholder;
import org.edgegamers.picklez.Listeners.*;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.mineacademy.fo.plugin.SimplePlugin;

public class EdgeSpleef extends SimplePlugin {

    private static Economy econ;

    @Override
    public void onPluginStart() {
        getLogger().info("Attempting to load oSpleef");
        getLogger().info("Version: 1.0.0-SNAPSHOT");
        getLogger().info("Author: Garrett#0001");
        getLogger().info("License: DEVELOPMENT");
        //Load Configuration
        getLogger().info("Attempting to load Configurations...");
        SpleefConfig config = new SpleefConfig();
        getLogger().info("Configurations loaded!");

        getLogger().info("Attempting to load commands...");
        registerCommand(new SpleefArenaCommand());
        registerCommand(new SpleefHelpCommand());
        registerCommand(new SpleefJoinCommand());
        registerCommand(new SpleefStartCommand());
        registerCommand(new SpleefLeaveCommand());
        getLogger().info("Done!");

        getLogger().info("Attempting to load listeners...");
        registerEvents(new onLiquidTouch());
        registerEvents(new SpleefonLogin());
        registerEvents(new SpleefonDisconnect());
        registerEvents(new onSignClick());
        registerEvents(new onItemHit());
        registerEvents(new onBlockBreak());
        registerEvents(new onCommand());
        registerEvents(new onSignCreation());
        getLogger().info("Done!");

        if (!setupVault()) {
            getLogger().severe("Error! You need vault to run this plugin! Please restart the server with vault to proceed!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Hooking into PlaceholderAPI");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SpleefPlaceholder(this).register();
        }
        getLogger().info("Done!");
    }

    public boolean setupVault() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if(rsp == null) {
            return false;
        }
        econ = rsp.getProvider();

        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
