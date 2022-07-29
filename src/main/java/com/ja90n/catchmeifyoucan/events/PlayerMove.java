package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PlayerMove(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if (catchMeIfYouCan.getArenaManager().getArena(event.getPlayer()) != null){
            if (catchMeIfYouCan.getArenaManager().getArena(event.getPlayer()).getGame().getSeekerCountdown().contains(event.getPlayer().getUniqueId())){
                if (!(event.getPlayer().getWorld().getBlockAt(event.getTo().add(0,-1,0)).getType().equals(Material.AIR))){
                    event.setCancelled(true);
                }
            }
        }
    }
}
