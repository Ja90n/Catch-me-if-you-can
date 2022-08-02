package com.ja90n.catchmeifyoucan.utils;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class SetScoreboard {
    public SetScoreboard(Player player, String type, CatchMeIfYouCan catchMeIfYouCan){
        if (type.equals("game")){
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective objective = scoreboard.registerNewObjective("Scoreboard","dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Catch me if you can");

            Score website = objective.getScore(ChatColor.LIGHT_PURPLE + "play.ja90n.software");
            website.setScore(1);

            Score space = objective.getScore(" ");
            space.setScore(2);

            Team timeUntilGlow = scoreboard.registerNewTeam("TimeToGlow");
            timeUntilGlow.addEntry(ChatColor.BLUE.toString());
            timeUntilGlow.setPrefix(ChatColor.YELLOW + "Time until glow: ");
            timeUntilGlow.setSuffix(ChatColor.WHITE + "0:00");
            objective.getScore(ChatColor.BLUE.toString()).setScore(3);

            Score space2 = objective.getScore("  ");
            space2.setScore(4);

            Team timeUntilWin = scoreboard.registerNewTeam("TimeToWin");
            timeUntilWin.addEntry(ChatColor.YELLOW.toString());
            timeUntilWin.setPrefix(ChatColor.BLUE + "Time until the runners win: ");
            timeUntilWin.setSuffix(ChatColor.WHITE + "0:00");
            objective.getScore(ChatColor.YELLOW.toString()).setScore(5);

            Score space3 = objective.getScore("    ");
            space3.setScore(6);

            Team hidersRemaining = scoreboard.registerNewTeam("HidersRemaining");
            hidersRemaining.addEntry(ChatColor.DARK_RED.toString());
            hidersRemaining.setPrefix(ChatColor.GREEN + "Hiders remaining: ");
            hidersRemaining.setSuffix(ChatColor.WHITE.toString() + catchMeIfYouCan.getArenaManager().getArena(player).getGame().getHiders().size());
            objective.getScore(ChatColor.DARK_RED.toString()).setScore(7);

            Score space4 = objective.getScore("     ");
            space3.setScore(8);

            player.setScoreboard(scoreboard);
        } else {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective objective = scoreboard.registerNewObjective("Scoreboard","dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Catch me if you can");

            Score website = objective.getScore(ChatColor.LIGHT_PURPLE + "play.ja90n.software");
            website.setScore(1);

            Score space = objective.getScore(" ");
            space.setScore(2);

            Team arenaPlayers = scoreboard.registerNewTeam("ArenaPlayers");
            arenaPlayers.addEntry(ChatColor.RED.toString());
            arenaPlayers.setPrefix(ChatColor.AQUA + "Player amount: ");
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                arenaPlayers.setSuffix(ChatColor.WHITE.toString() + catchMeIfYouCan.getArenaManager().getArena(player).getPlayers().size());
            } else {
                arenaPlayers.setSuffix(ChatColor.WHITE + "No arena?");
            }
            objective.getScore(ChatColor.RED.toString()).setScore(3);

            Team arena = scoreboard.registerNewTeam("Arena");
            arena.addEntry(ChatColor.LIGHT_PURPLE.toString());
            arena.setPrefix(ChatColor.DARK_BLUE + "Current arena: ");
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                arena.setSuffix(ChatColor.WHITE + catchMeIfYouCan.getArenaManager().getArena(player).getName());
            } else {
                arena.setSuffix(ChatColor.WHITE + "None");
            }
            objective.getScore(ChatColor.LIGHT_PURPLE.toString()).setScore(4);

            Score space3 = objective.getScore("     ");
            space3.setScore(5);

            player.setScoreboard(scoreboard);
        }
    }
}
