package com.ja90n.catchmeifyoucan.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerHunger implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        event.setFoodLevel(20);
    }
}
