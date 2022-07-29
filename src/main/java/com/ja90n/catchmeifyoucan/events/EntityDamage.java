package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public EntityDamage(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                Arena arena = catchMeIfYouCan.getArenaManager().getArena(player);
                if (arena.getGame().getHiders().contains(player.getUniqueId())){
                    if (arena.getGame().getHiders().contains(event.getDamager().getUniqueId())){
                        event.setCancelled(true);
                    }
                } else if (arena.getGame().getSeekers().contains(player.getUniqueId())){
                    if (arena.getGame().getSeekers().contains(event.getDamager().getUniqueId())){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
