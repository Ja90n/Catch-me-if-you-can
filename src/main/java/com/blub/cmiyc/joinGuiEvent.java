package com.blub.cmiyc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class joinGuiEvent implements Listener {

    private Cmiyc cmiyc;
    public joinGuiEvent(Cmiyc cmiyc) {
        this.cmiyc = cmiyc;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Catch me if you can - Main menu")){
            if (e.getSlot() == 20){
                if (e.getInventory().getItem(20).getType().equals(Material.STONE_SWORD)){
                    if (cmiyc.issGameActive()){
                        p.sendMessage(ChatColor.RED + "There is already a game active!");
                        p.closeInventory();
                    } else {
                        //Make a player join the lobby
                        cmiyc.getLobbyplayers().add(p.getUniqueId());
                        p.sendMessage(ChatColor.GREEN + "You have joined the game!");
                        p.closeInventory();
                    }
                } else {
                    if (cmiyc.issGameActive()){

                    } else {
                        //Make a player leave the lobby
                        cmiyc.getLobbyplayers().remove(p.getUniqueId());
                        p.sendMessage(ChatColor.RED + "You have left the game!");
                        p.closeInventory();
                    }
                }
            }
        }
    }
}
