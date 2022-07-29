package com.ja90n.catchmeifyoucan.managers;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager (CatchMeIfYouCan catchMeIfYouCan){
        try {
            FileConfiguration config = catchMeIfYouCan.getConfig();
            for (String str : config.getConfigurationSection("arenas.").getKeys(false)){
                arenas.add(new Arena(catchMeIfYouCan, Integer.parseInt(str),config.getString("arenas." + str + ".name")));
            }
        } catch (NullPointerException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "The config file is not set up correctly");
        }
    }

    public List<Arena> getArenas() { return arenas; }

    public Arena getArena(Player player){
        for (Arena arena : arenas){
            if (arena.getPlayers().contains(player.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for (Arena arena : arenas){
            if (arena.getId() == id){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(String name){
        for (Arena arena : arenas){
            if (arena.getName().equals(name)){
                return arena;
            }
        }
        return null;
    }
}
