package com.blub.cmiyc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class miscellaneousEvents implements Listener {

    private Cmiyc cmiyc;
    public miscellaneousEvents(Cmiyc cmiyc) {
        this.cmiyc = cmiyc;
    }

    @EventHandler
    public void onClick (InventoryClickEvent e){
        if (cmiyc.issGameActive()){
            if (e.getWhoClicked() instanceof Player){
                if (e.getSlotType() == InventoryType.SlotType.ARMOR){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage (EntityDamageByEntityEvent e){
        if (cmiyc.issGameActive()) {
            if (e.getEntity() instanceof Player) {
                if (e.getDamager() instanceof Player) {
                    Player p1 = (Player) e.getEntity();
                    Player p2 = (Player) e.getDamager();
                    if (cmiyc.getHiderplayers().contains(p1.getUniqueId())) {
                        if (cmiyc.getHiderplayers().contains(p2.getUniqueId())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
            if (e.getDamager() instanceof Player){
                Player sp = (Player) e.getDamager();
                if (cmiyc.getSpectatorplayers().contains(sp.getUniqueId())){
                    e.setCancelled(true);
                }
            }
            if (e.getEntity() instanceof Minecart){
                e.setCancelled(true);
            }
        } else {
            if (e.getDamager() instanceof Player){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onInteract (PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (!cmiyc.issGameActive()) {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (!p.getPassengers().isEmpty()) {
                    Entity entity = p.getPassengers().get(0);
                    p.removePassenger(entity);
                    entity.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                }
            }
        }
    }

    @EventHandler
    public void onBed (PlayerBedEnterEvent e){
        if (cmiyc.issGameActive()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if (cmiyc.issGameActive()) {
            if (e.getItemDrop().getItemStack().getType().equals(Material.STONE_SWORD)) {
                e.setCancelled(true);
            } else if (e.getItemDrop().getItemStack().getType().equals(Material.COMPASS)) {
                e.setCancelled(true);
            }
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onPick(EntityPickupItemEvent e){
        if (e.getEntity() instanceof Player){
            if (cmiyc.isgameactive){
                e.getItem().remove();
            }
        }
    }

    @EventHandler
    public void painitngBreakd(HangingBreakByEntityEvent e){
        if (cmiyc.issGameActive()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick (PlayerInteractAtEntityEvent e) {
        if (!cmiyc.issGameActive()) {
            if (!e.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
                if (e.getPlayer().isSneaking()) {
                    e.getPlayer().addPassenger(e.getRightClicked());
                } else {
                    e.getRightClicked().addPassenger(e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onminecart(VehicleDamageEvent e){
        if (!cmiyc.issGameActive()){
            e.setCancelled(true);
        }
    }
}
