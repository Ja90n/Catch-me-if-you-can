package com.ja90n.catchmeifyoucan.utils;

import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class WinCheckUtil {
    public WinCheckUtil(Arena arena){
        if (arena.getGame().getHiders().size() <= 0){
            arena.sendMessage(ChatColor.DARK_RED + "The catchers have won the game!");
            arena.sendTitle(ChatColor.DARK_RED + "The catchers have won the game!",ChatColor.GRAY + "Thank you for playing!");
            arena.stopGame();
        } else if (arena.getGame().getSeekers().size() <= 0){
            arena.sendMessage(ChatColor.BLUE + "Runners have won the game!");
            arena.sendTitle(ChatColor.BLUE + "Runners have won the game!",ChatColor.GRAY + "Thank you for playing!");
            arena.stopGame();
        }
    }
}