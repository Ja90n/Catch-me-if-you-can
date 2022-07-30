package com.ja90n.catchmeifyoucan;

import com.ja90n.catchmeifyoucan.commands.MainCommand;
import com.ja90n.catchmeifyoucan.commands.MainCommandTabCompleter;
import com.ja90n.catchmeifyoucan.events.*;
import com.ja90n.catchmeifyoucan.managers.ArenaManager;
import com.ja90n.catchmeifyoucan.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CatchMeIfYouCan extends JavaPlugin {

    private ConfigManager configManager;
    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        configManager = new ConfigManager(this);
        arenaManager = new ArenaManager(this);

        getCommand("cmiyc").setExecutor(new MainCommand(this));
        getCommand("cmiyc").setTabCompleter(new MainCommandTabCompleter(this));

        getCommand("catch").setExecutor(new MainCommand(this));
        getCommand("catch").setTabCompleter(new MainCommandTabCompleter(this));

        getServer().getPluginManager().registerEvents(new PlayerMove(this),this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this),this);
        getServer().getPluginManager().registerEvents(new EntityDamage(this),this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this),this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(this),this);
        getServer().getPluginManager().registerEvents(new PlayerInventory(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}
