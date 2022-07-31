package com.ja90n.catchmeifyoucan.managers;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;
    private CatchMeIfYouCan catchMefYouCan;

    public ConfigManager(CatchMeIfYouCan catchMeIfYouCan){
        config = catchMeIfYouCan.getConfig();
    }

    public Location getLobbySpawn(int id){
        return new Location(
                Bukkit.getWorld(config.getString("arenas." + id + ".lobby-spawn.world")),
                config.getDouble("arenas." + id + ".lobby-spawn.x"),
                config.getDouble("arenas." + id + ".lobby-spawn.y"),
                config.getDouble("arenas." + id + ".lobby-spawn.z"),
                (float) config.getDouble("arenas." + id + ".lobby-spawn.yaw"),
                (float) config.getDouble("arenas." + id + ".lobby-spawn.pitch"));
    }

    public Location getHiderSpawn(int id){
        return new Location(
                Bukkit.getWorld(config.getString("arenas." + id + ".hider-spawn.world")),
                config.getDouble("arenas." + id + ".hider-spawn.x"),
                config.getDouble("arenas." + id + ".hider-spawn.y"),
                config.getDouble("arenas." + id + ".hider-spawn.z"),
                (float) config.getDouble("arenas." + id + ".hider-spawn.yaw"),
                (float) config.getDouble("arenas." + id + ".hider-spawn.pitch"));
    }

    public Location getSeekerSpawn(int id){
        return new Location(
                Bukkit.getWorld(config.getString("arenas." + id + ".seeker-spawn.world")),
                config.getDouble("arenas." + id + ".seeker-spawn.x"),
                config.getDouble("arenas." + id + ".seeker-spawn.y"),
                config.getDouble("arenas." + id + ".seeker-spawn.z"),
                (float) config.getDouble("arenas." + id + ".seeker-spawn.yaw"),
                (float) config.getDouble("arenas." + id + ".seeker-spawn.pitch"));
    }

    public Location getSpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("spawn.world")),
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                (float) config.getDouble("spawn.yaw"),
                (float) config.getDouble("spawn.pitch"));
    }

    public int getRequiredPlayers(){
        return config.getInt("required-players");
    }

    public int getMatchTime(){return config.getInt("match-time");}
    public int getSeekerWaitTime(){
        return config.getInt("seeker-wait-time");
    }
    public int getCountdownTime(){
        return config.getInt("countdown-time");
    }
}
