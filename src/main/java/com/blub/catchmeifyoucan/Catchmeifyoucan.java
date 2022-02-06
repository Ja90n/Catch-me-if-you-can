package com.blub.catchmeifyoucan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Catchmeifyoucan extends JavaPlugin {


    @Override
    public void onEnable() {
        // Startup the Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Commands
        getCommand("catch").setExecutor(new catchCommand(this));
        getCommand("catch").setTabCompleter(new catchTabCompleter());

        //Events
        getServer().getPluginManager().registerEvents(new catchEvents(this),this);

        //started up the project
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "'Catch me if you can' plugin has stopped!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing!");
    }

    @Override
    public void onDisable() {
        for (Player target : Bukkit.getOnlinePlayers()){
            target.removePotionEffect(PotionEffectType.INVISIBILITY);
            target.removePotionEffect(PotionEffectType.SPEED);
            target.setAllowFlight(false);
            target.setFlying(false);
            if (spectator.contains(target.getUniqueId())){
                for (Player target2 : Bukkit.getOnlinePlayers()){
                    target2.showPlayer(target);
                }
            }
            target.getInventory().clear();
        }
        for (Player target : Bukkit.getOnlinePlayers()){
            if (seeker.contains(target.getUniqueId())){
                seeker.remove(target.getUniqueId());
            } else if (hider.contains(target.getUniqueId())){
                hider.remove(target.getUniqueId());
            } else if (spectator.contains(target.getUniqueId())){
                spectator.remove(target.getUniqueId());
            }
        }
        gameactive = false;
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "'Catch me if you can' plugin has stopped!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing!");
    }

    //Lists of what players are in what teams
    List<UUID> seeker = new ArrayList<>();
    List<UUID> hider = new ArrayList<>();
    List<UUID> spectator = new ArrayList<>();
    //Is a game active boolean
    boolean gameactive = false;


    //Used so other classes can acces the lists
    public List<UUID> getSeekerList() {return this.seeker;}
    public List<UUID> getHiderList() {return this.hider;}
    public List<UUID> getSpectatorList(){return this.spectator;}
    public boolean getIsGame(){return this.gameactive;}
}
