package com.ja90n.catchmeifyoucan.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class MiscEvents implements Listener {

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event){
        event.setFoodLevel(20);
        event.setCancelled(true);
    }
}
