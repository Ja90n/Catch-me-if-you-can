package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

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
        if (countdownTime == 0){
            arena.sendMessage(ChatColor.BLUE + "Runners have won the game!");
            arena.sendTitle(ChatColor.BLUE + "Runners have won the game!",ChatColor.GRAY + "Thank you for playing!");
            arena.stopGame();
            cancel();
        }
        if (countdownTime <= 10 || countdownTime % 15 == 0){
            arena.sendMessage(ChatColor.BLUE + "Runners will win in " + countdownTime + " second" + (countdownTime == 1 ? "" : "s") + ".");
        }
        countdownTime--;
    }
}
