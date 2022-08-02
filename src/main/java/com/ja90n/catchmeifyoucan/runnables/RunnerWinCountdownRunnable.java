package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RunnerWinCountdownRunnable extends BukkitRunnable {

    private CatchMeIfYouCan catchMeIfYouCan;
    private Arena arena;
    private int countdownTime;

    public RunnerWinCountdownRunnable(CatchMeIfYouCan catchMeIfYouCan, Arena arena){
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.arena = arena;
    }

    public void start(){
        countdownTime = catchMeIfYouCan.getConfigManager().getMatchTime();
        runTaskTimer(catchMeIfYouCan,0,20);
    }

    @Override
    public void run() {
        for (UUID uuid : arena.getPlayers()){
            Player player = Bukkit.getPlayer(uuid);
            if (countdownTime >= 60){
                int minutes = countdownTime/60;
                int seconds = countdownTime - (60*minutes);
                if (seconds <= 9){
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + minutes + ":0" + seconds);
                } else {
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + minutes + ":" + seconds);
                }
            } else {
                if (countdownTime <= 9){
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + "00:0" + countdownTime);
                } else {
                    player.getScoreboard().getTeam("TimeToWin").setSuffix(ChatColor.WHITE.toString() + "00:" + countdownTime);
                }
            }
        }
        if (countdownTime == 0){
            arena.sendMessage(ChatColor.BLUE + "Runners have won the game!");
            arena.sendTitle(ChatColor.BLUE + "Runners have won the game!",ChatColor.GRAY + "Thank you for playing!");
            arena.stopGame();
            cancel();
        }
        if (countdownTime % 60 == 0){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in " + countdownTime/60 + " minute" + (countdownTime/60 == 1 ? "" : "s") + ".");
        }
        if (countdownTime == 30){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in 30 seconds");
        }
        if (countdownTime <= 10){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in " + countdownTime + " second" + (countdownTime == 1 ? "" : "s") + ".");
        }
        countdownTime--;
    }
}
