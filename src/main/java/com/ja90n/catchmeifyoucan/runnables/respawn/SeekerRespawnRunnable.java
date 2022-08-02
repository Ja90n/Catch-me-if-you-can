package com.ja90n.catchmeifyoucan.runnables.respawn;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.utils.SetupPlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SeekerRespawnRunnable extends BukkitRunnable {

    private Player player;
    private CatchMeIfYouCan catchMeIfYouCan;
    private int timeRun;

    public SeekerRespawnRunnable(Player player, CatchMeIfYouCan catchMeIfYouCan){
        this.player = player;
        this.catchMeIfYouCan = catchMeIfYouCan;
        timeRun = 5;
        runTaskTimer(catchMeIfYouCan,2,20);
    }

    @Override
    public void run() {
        player.sendTitle(ChatColor.BLUE + "You will respawn in " + ChatColor.WHITE + timeRun + " seconds!"," ");
        player.sendMessage(ChatColor.BLUE + "You will respawn in " + ChatColor.WHITE + timeRun + " seconds!");
        if (timeRun == 0){
            player.setInvisible(false);
            player.setInvulnerable(false);
            player.sendTitle("","");
            new SetupPlayerUtil(player,"seeker",catchMeIfYouCan);
            player.teleport(catchMeIfYouCan.getConfigManager().getSeekerSpawn(catchMeIfYouCan.getArenaManager().getArena(player).getId()));
            cancel();
        } else if (timeRun == 5){
            player.setInvisible(true);
            player.setInvulnerable(true);
        }
        timeRun--;
    }
}
