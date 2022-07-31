package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import com.ja90n.catchmeifyoucan.runnables.LaunchPlayerRunnable;
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
                        target.addPassenger(player);
                    }
                } else if (arena.getGame().getSeekers().contains(player.getUniqueId())){
                    if (arena.getGame().getSeekers().contains(target.getUniqueId())){
                        target.addPassenger(player);
                    }
                }
            } else {
                target.addPassenger(player);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.getPassengers().isEmpty()){
            Entity entity = player.getPassengers().get(0);
            player.removePassenger(entity);
            new LaunchPlayerRunnable(catchMeIfYouCan,player.getLocation().getDirection().multiply(1.5).setY(1.2),entity);
        }
    }

    @EventHandler
    public void onBedInteract(PlayerBedEnterEvent event){
        if (catchMeIfYouCan.getArenaManager().getArena(event.getPlayer()) != null){
            event.setCancelled(true);
        }
    }
}
