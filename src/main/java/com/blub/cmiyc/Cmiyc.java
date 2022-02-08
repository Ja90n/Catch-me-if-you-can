package com.blub.cmiyc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public final class Cmiyc extends JavaPlugin {

    List<UUID> lobbyplayers = new ArrayList<>();
    List<UUID> seekerplayers = new ArrayList<>();
    List<UUID> hiderplayers = new ArrayList<>();
    List<UUID> spectatorplayers = new ArrayList<>();
    boolean isgameactive = false;
    Map<UUID, String> colourMap = new HashMap<>();


    @Override
    public void onEnable() {
        //Get config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Events
        getServer().getPluginManager().registerEvents(new mainGuiEvent(), this);
        getServer().getPluginManager().registerEvents(new joinGuiEvent(this), this);
        getServer().getPluginManager().registerEvents(new colourSelectGuiEvent(this), this);
        getServer().getPluginManager().registerEvents(new gamemenuGuiEvent(this), this);
        getServer().getPluginManager().registerEvents(new onDeathandRespawnEvents(this),this);
        getServer().getPluginManager().registerEvents(new miscellaneousEvents(this),this);
        getServer().getPluginManager().registerEvents(new spectatorCompasEvent(this),this);

        //Command
        getCommand("catch").setExecutor(new cmiycCommands(this));

        //Startup message
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "CMIYC Plugin loaded succesfully!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Thank you for playing!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "- Blubdev");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Stopping the game
        for (Player target : Bukkit.getOnlinePlayers()){
            if (this.getHiderplayers().contains(target.getUniqueId())){
                this.getHiderplayers().remove(target.getUniqueId());
                target.getInventory().clear();
            } else if (this.getSeekerplayers().contains(target.getUniqueId())){
                this.getSeekerplayers().remove(target.getUniqueId());
                target.getInventory().clear();
            } else if (this.getSpectatorplayers().contains(target.getUniqueId())) {
                this.getSpectatorplayers().remove(target.getUniqueId());
                target.getInventory().clear();
            }

            //Removing effects
            for (Player target2 : Bukkit.getOnlinePlayers()){
                target.showPlayer(target2);
                target.removePotionEffect(PotionEffectType.INVISIBILITY);
                target.removePotionEffect(PotionEffectType.SPEED);
                target.setAllowFlight(false);
                target.setFlying(false);
                target.getInventory().clear();
            }

            //Teleporting player
            double hx = this.getConfig().getDouble("lobby.x");
            double hy = this.getConfig().getDouble("lobby.y");
            double hz = this.getConfig().getDouble("lobby.z");
            Location lobby = new Location(Bukkit.getWorld(this.getConfig().getString("world")), hx, hy, hz, 0, 0);
            target.teleport(lobby);

            //Sending message
            target.sendMessage(ChatColor.BLUE + "Game has been stoped!");
        }
    }

    public boolean issGameActive() {return this.isgameactive;}
    public List<UUID> getLobbyplayers() {return this.lobbyplayers;}
    public List<UUID> getSeekerplayers() {return this.seekerplayers;}
    public List<UUID> getHiderplayers() {return this.hiderplayers;}
    public List<UUID> getSpectatorplayers() {return this.spectatorplayers;}
    public Map<UUID, String> colourMap() {return this.colourMap;}
}
