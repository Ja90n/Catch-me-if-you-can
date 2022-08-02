package com.ja90n.catchmeifyoucan.utils;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadScoreboard {
    public ReloadScoreboard(CatchMeIfYouCan catchMeIfYouCan,Player player){
        if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
            player.getScoreboard().getTeam("Arena").setSuffix(ChatColor.WHITE +
                    catchMeIfYouCan.getArenaManager().getArena(player).getName());
        } else {
            player.getScoreboard().getTeam("Arena").setSuffix(ChatColor.WHITE + "None");
        }

        for (Player player1 : Bukkit.getOnlinePlayers()){
            if (catchMeIfYouCan.getArenaManager().getArena(player1) != null){
                player1.getScoreboard().getTeam("ArenaPlayers").setSuffix(ChatColor.WHITE.toString() +
                        catchMeIfYouCan.getArenaManager().getArena(player1).getPlayers().size());
            }
        }
    }
}
