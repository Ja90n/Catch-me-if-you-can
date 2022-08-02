package com.ja90n.catchmeifyoucan.runnables.respawn;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.instances.Arena;
import com.ja90n.catchmeifyoucan.utils.SetupPlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HiderRespawnRunnable extends BukkitRunnable {

    private Player player;
    private CatchMeIfYouCan catchMeIfYouCan;

    public HiderRespawnRunnable(Player player, CatchMeIfYouCan catchMeIfYouCan){
        this.player = player;
        this.catchMeIfYouCan = catchMeIfYouCan;
        runTaskLater(catchMeIfYouCan,5);
    }

    @Override
    public void run() {
        if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
            player.teleport(catchMeIfYouCan.getConfigManager().getHiderSpawn(catchMeIfYouCan.getArenaManager().getArena(player).getId()));
            Bukkit.getConsoleSender().sendMessage("teleport after death arena");
        } else {
            player.teleport(catchMeIfYouCan.getConfigManager().getSpawn());
            Bukkit.getConsoleSender().sendMessage("teleport after death spawn");
        }
    }
}
