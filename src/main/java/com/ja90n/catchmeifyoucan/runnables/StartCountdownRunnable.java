package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class StartCountdownRunnable extends BukkitRunnable {

    private CatchMeIfYouCan catchMeIfYouCan;
    private int countdownTime;
    private Arena arena;

    public StartCountdownRunnable(CatchMeIfYouCan catchMeIfYouCan,Arena arena){
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.arena = arena;
        this.countdownTime = catchMeIfYouCan.getConfigManager().getCountdownTime();

    }

    public void start(){
        arena.setGameState(GameState.COUNTDOWN);
        runTaskTimer(catchMeIfYouCan,0,20);
    }

    @Override
    public void run() {
        if (countdownTime == 0){
            cancel();
            arena.startGame();
            return;
        }
        if (countdownTime <= 10 || countdownTime % 15 == 0){
            arena.sendMessage(ChatColor.BLUE + "Game will start in " + ChatColor.WHITE + countdownTime + ChatColor.BLUE + " second" + (countdownTime == 1 ? "" : "s") + ".");
        }
        arena.sendTitle(ChatColor.BLUE + "Game will start in " + ChatColor.WHITE + countdownTime + ChatColor.BLUE + " second" + (countdownTime == 1 ? "" : "s") + "."," ");
        countdownTime--;
    }
}