package com.blub.cmiyc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class mainGuiEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Catch me if you can - Main menu")){
        e.setCancelled(true);
        }
    }
}
