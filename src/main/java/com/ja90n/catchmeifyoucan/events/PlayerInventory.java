package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerInventory implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PlayerInventory(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player){
            if (catchMeIfYouCan.getArenaManager().getArena((Player) event.getWhoClicked()) != null) {
                if (event.getSlotType() == InventoryType.SlotType.ARMOR){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop (PlayerDropItemEvent event){
        if (catchMeIfYouCan.getArenaManager().getArena(event.getPlayer()) != null){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp (EntityPickupItemEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                event.getItem().remove();
            }
        }
    }
}
