package com.blub.cmiyc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class spectatorCompasEvent implements Listener {

    private Cmiyc cmiyc;
    public spectatorCompasEvent(Cmiyc cmiyc) {
        this.cmiyc = cmiyc;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e){
        if (cmiyc.issGameActive()){
            Player p = e.getPlayer();
            if (p.getInventory().getItemInMainHand().equals(new ItemStack(Material.COMPASS))){
                if (cmiyc.getSpectatorplayers().contains(p.getUniqueId())) {
                    e.setCancelled(true);
                    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Inventory spectatorCompass = Bukkit.createInventory(p, 45, ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Spectator Compass");

                        //Creation of the frame of the main gui menu
                        ItemStack frame = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
                        ItemMeta framemeta = frame.getItemMeta();
                        framemeta.setDisplayName("");
                        frame.setItemMeta(framemeta);
                        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}) {
                            spectatorCompass.setItem(i, frame);
                        }

                        ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta playermeta = (SkullMeta) playerhead.getItemMeta();

                        for (Player target : Bukkit.getOnlinePlayers()) {
                            if (!cmiyc.getSpectatorplayers().contains(target.getUniqueId())) {
                                if (cmiyc.getSeekerplayers().contains(target.getUniqueId())) {
                                    playermeta.setOwningPlayer(target);
                                    playermeta.setDisplayName(ChatColor.RED + target.getDisplayName());
                                    playerhead.setItemMeta(playermeta);
                                    for (int i : new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34}) {
                                        if (!spectatorCompass.contains(Material.PLAYER_HEAD, i)) {
                                            spectatorCompass.setItem(i, playerhead);
                                            break;
                                        }
                                    }
                                } else if (cmiyc.getHiderplayers().contains(target.getUniqueId())) {
                                    playermeta.setOwningPlayer(target);
                                    playermeta.setDisplayName(ChatColor.GREEN + target.getDisplayName());
                                    playerhead.setItemMeta(playermeta);
                                    for (int i : new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34}) {
                                        if (!spectatorCompass.contains(Material.PLAYER_HEAD, i)) {
                                            spectatorCompass.setItem(i, playerhead);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        p.openInventory(spectatorCompass);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoyClick(InventoryClickEvent e){
        if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Spectator Compass")){
            e.setCancelled(true);
            if (e.getCurrentItem().equals(new ItemStack (Material.PLAYER_HEAD))){
                for (Player target : Bukkit.getOnlinePlayers()){
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + target.getDisplayName())){
                        e.getWhoClicked().teleport(target);
                        e.getWhoClicked().sendMessage(ChatColor.BLUE + "You have been teleported to " + ChatColor.RED.toString() + ChatColor.BOLD + target.getDisplayName());
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + target.getDisplayName())){
                        e.getWhoClicked().teleport(target);
                        e.getWhoClicked().sendMessage(ChatColor.BLUE + "You have been teleported to " + ChatColor.GREEN.toString() + ChatColor.BOLD + target.getDisplayName());
                    }
                    e.getWhoClicked().closeInventory();
                }
            }
        }
    }
}
