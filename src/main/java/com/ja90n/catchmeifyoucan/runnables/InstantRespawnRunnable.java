package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InstantRespawnRunnable extends BukkitRunnable {

    private Player player;

    public InstantRespawnRunnable(CatchMeIfYouCan catchMeIfYouCan, Player player){
        this.player = player;
        runTaskLater(catchMeIfYouCan,2);
    }

    @Override
    public void run() {
        player.spigot().respawn();
    }
}
