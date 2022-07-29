package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PlayerQuit(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if (catchMeIfYouCan.getArenaManager().getArena(event.getPlayer()) != null){
            Arena arena = catchMeIfYouCan.getArenaManager().getArena(event.getPlayer());
            event.setQuitMessage(event.getPlayer().getDisplayName() + ChatColor.RED + " has left the game");
            arena.removePlayer(event.getPlayer());
        }
    }
}
