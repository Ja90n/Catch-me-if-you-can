package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PlayerInteract(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event){
        if (event.getRightClicked() instanceof Player){
            Player player = event.getPlayer();
            Player target = (Player) event.getRightClicked();
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                Arena arena = catchMeIfYouCan.getArenaManager().getArena(player);
                if (arena.getGame().getHiders().contains(player.getUniqueId())){
                    if (arena.getGame().getHiders().contains(target.getUniqueId())){
                        if (player.isSneaking()){
                            player.addPassenger(target);
                        } else {
                            target.addPassenger(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
            Arena arena = catchMeIfYouCan.getArenaManager().getArena(player);
            if (player.getPassengers().get(0) != null){
                Entity entity = player.getPassengers().get(0);
                player.removePassenger(entity);
                entity.setVelocity(player.getLocation().getDirection().multiply(2).setY(1.5));
            }
        }
    }

    @EventHandler
    public void onBedInteract(PlayerBedEnterEvent event){
        if (catchMeIfYouCan.getArenaManager().getArena(event.getPlayer()) != null){
            event.setCancelled(true);
        }
    }
}
