package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class EntityDamage implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public EntityDamage(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                if (catchMeIfYouCan.getArenaManager().getArena(player).getGameState().equals(GameState.LIVE)){
                    Arena arena = catchMeIfYouCan.getArenaManager().getArena(player);
                    if (arena.getGame().getHiders().contains(player.getUniqueId())){
                        if (arena.getGame().getHiders().contains(event.getDamager().getUniqueId())){
                            event.setCancelled(true);
                        }
                    } else if (arena.getGame().getSeekers().contains(player.getUniqueId())){
                        if (arena.getGame().getSeekers().contains(event.getDamager().getUniqueId())){
                            event.setCancelled(true);
                        }
                    } else {
                        event.setCancelled(true);
                        event.setDamage(0);
                    }
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        } else {

        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            event.setDamage(event.getDamage()/3);
        }
    }

    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent event){
        if (event.getAttacker() instanceof Player){
            Player player = (Player) event.getAttacker();
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPaintingDamage(HangingBreakByEntityEvent event){
        if (event.getRemover() instanceof Player){
            Player player = (Player) event.getRemover();
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                event.setCancelled(true);
            }
        }
    }
}
