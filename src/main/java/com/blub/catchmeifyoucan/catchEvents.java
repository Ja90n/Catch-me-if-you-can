package com.blub.catchmeifyoucan;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

public class catchEvents implements Listener {

    private Catchmeifyoucan catchmeifyoucan;

    public catchEvents(Catchmeifyoucan catchmeifyoucan) {
        this.catchmeifyoucan = catchmeifyoucan;
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e){
        e.setKeepInventory(true);
        if (catchmeifyoucan.getIsGame()){
            Player p = e.getEntity().getPlayer();

            if (catchmeifyoucan.getSeekerList().contains(p.getUniqueId())){
                e.setDeathMessage(ChatColor.GREEN + "Seeker " + ChatColor.RED + p.getDisplayName() + ChatColor.GREEN + " was killed!");
            } else if (catchmeifyoucan.getHiderList().contains(p.getUniqueId())){
                catchmeifyoucan.getHiderList().remove(p.getUniqueId());
                catchmeifyoucan.getSpectatorList().add(p.getUniqueId());
                 if (catchmeifyoucan.getHiderList().size() == 1){
                     Player winner = Bukkit.getPlayer(catchmeifyoucan.getHiderList().get(0));
                     double sx = catchmeifyoucan.getConfig().getDouble("lobby.x");
                     double sy = catchmeifyoucan.getConfig().getDouble("lobby.y");
                     double sz = catchmeifyoucan.getConfig().getDouble("lobby.z");
                     Location lobby = new Location(Bukkit.getWorld("world"), sx, sy, sz, 0, 0);
                     for (Player target : Bukkit.getOnlinePlayers()){
                         target.sendMessage(winner.getDisplayName() + ChatColor.BLUE + " has won the game!");
                         target.removePotionEffect(PotionEffectType.INVISIBILITY);
                         target.removePotionEffect(PotionEffectType.SPEED);
                         target.setAllowFlight(false);
                         target.setFlying(false);
                         if (catchmeifyoucan.getSpectatorList().contains(target.getUniqueId())){
                             for (Player target2 : Bukkit.getOnlinePlayers()){
                                 target2.showPlayer(target);
                             }
                         }
                         target.teleport(lobby);
                         target.getInventory().clear();
                     }
                     for (Player target : Bukkit.getOnlinePlayers()){
                         if (catchmeifyoucan.getSeekerList().contains(target.getUniqueId())){
                             catchmeifyoucan.getSeekerList().remove(target.getUniqueId());
                         } else if (catchmeifyoucan.getHiderList().contains(target.getUniqueId())){
                             catchmeifyoucan.getHiderList().remove(target.getUniqueId());
                         } else if (catchmeifyoucan.getSpectatorList().contains(target.getUniqueId())){
                             catchmeifyoucan.getSpectatorList().remove(target.getUniqueId());
                         }
                     }
                     catchmeifyoucan.gameactive = false;
                 } else {
                     e.setDeathMessage(p.getDisplayName() + ChatColor.BLUE + " got caught! There are "  + ChatColor.GREEN + + catchmeifyoucan.getHiderList().size() + ChatColor.BLUE + " hiders left!");
                 }
            } else if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())){

            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if (catchmeifyoucan.getIsGame()){
            Player p = e.getPlayer();
            if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())){
                double sx = catchmeifyoucan.getConfig().getDouble("spectatorspawn.x");
                double sy = catchmeifyoucan.getConfig().getDouble("spectatorspawn.y");
                double sz = catchmeifyoucan.getConfig().getDouble("spectatorspawn.z");
                Location spectatorspawn = new Location(Bukkit.getWorld("world"), sx, sy, sz, 0, 0);
                e.setRespawnLocation(spectatorspawn);
                Player target = Bukkit.getPlayer(catchmeifyoucan.getSpectatorList().get(0));
                e.setRespawnLocation(target.getLocation());
                e.getPlayer().setAllowFlight(true);
                e.getPlayer().setFlying(true);
                //onrespawn set the player to adventure and vanish him for everyone but the specator
                p.setGameMode(GameMode.ADVENTURE);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (catchmeifyoucan.getSeekerList().contains(onlinePlayer.getUniqueId())) {
                        onlinePlayer.hidePlayer(p);
                    } else if (catchmeifyoucan.getHiderList().contains(onlinePlayer.getUniqueId())) {
                        onlinePlayer.hidePlayer(p);
                    } else if (catchmeifyoucan.getSpectatorList().contains(onlinePlayer.getUniqueId())) {
                        onlinePlayer.showPlayer(p);
                    }
                }
            }
        } else {
            double sx = catchmeifyoucan.getConfig().getDouble("lobby.x");
            double sy = catchmeifyoucan.getConfig().getDouble("lobby.y");
            double sz = catchmeifyoucan.getConfig().getDouble("lobby.z");
            Location lobby = new Location(Bukkit.getWorld("world"), sx, sy, sz, 0, 0);
            e.setRespawnLocation(lobby);
        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent e){
        if (catchmeifyoucan.getIsGame()){
            if (e.getWhoClicked() instanceof Player){
                if (e.getSlotType() == InventoryType.SlotType.ARMOR){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage (EntityDamageByEntityEvent e){
        if (catchmeifyoucan.getIsGame()) {
            if (e.getEntity() instanceof Player) {
                if (e.getDamager() instanceof Player) {
                    Player p1 = (Player) e.getEntity();
                    Player p2 = (Player) e.getDamager();
                    if (catchmeifyoucan.getHiderList().contains(p1.getUniqueId())) {
                        if (catchmeifyoucan.getHiderList().contains(p2.getUniqueId())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
            if (e.getDamager() instanceof Player){
                Player sp = (Player) e.getDamager();
                if (catchmeifyoucan.getSpectatorList().contains(sp.getUniqueId())){
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
    public void onBed (PlayerBedEnterEvent e){
        if (catchmeifyoucan.getIsGame()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if (e.getItemDrop().getItemStack().getType().equals(Material.STONE_SWORD)){
            e.setCancelled(true);
        }
        e.getItemDrop().remove();
    }

    @EventHandler
    public void onPick(EntityPickupItemEvent e){
        if (e.getEntity() instanceof Player){
            if (catchmeifyoucan.gameactive){
                e.getItem().remove();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (catchmeifyoucan.gameactive){
            p.getInventory().clear();
            if (catchmeifyoucan.getHiderList().contains(p.getUniqueId())){
                catchmeifyoucan.getHiderList().remove(p.getUniqueId());
                p.setHealth(0);
            } else if (catchmeifyoucan.getSeekerList().contains(p.getUniqueId())) {
                catchmeifyoucan.getSeekerList().remove(p.getUniqueId());
                p.setHealth(0);
            } else if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())){
                catchmeifyoucan.getSpectatorList().remove(p.getUniqueId());
                p.setHealth(0);
            }
        }
        catchmeifyoucan.getHiderList().remove(p.getUniqueId());
        catchmeifyoucan.getSpectatorList().add(p.getUniqueId());
        if (catchmeifyoucan.getHiderList().size() == 1){
            Player winner = Bukkit.getPlayer(catchmeifyoucan.getHiderList().get(0));
            double sx = catchmeifyoucan.getConfig().getDouble("lobby.x");
            double sy = catchmeifyoucan.getConfig().getDouble("lobby.y");
            double sz = catchmeifyoucan.getConfig().getDouble("lobby.z");
            Location lobby = new Location(Bukkit.getWorld("world"), sx, sy, sz, 0, 0);
            for (Player target : Bukkit.getOnlinePlayers()){
                target.sendMessage(winner.getDisplayName() + ChatColor.BLUE + " has won the game because of a ragequitter");
                target.removePotionEffect(PotionEffectType.INVISIBILITY);
                target.removePotionEffect(PotionEffectType.SPEED);
                target.setAllowFlight(false);
                target.setFlying(false);
                if (catchmeifyoucan.getSpectatorList().contains(target.getUniqueId())){
                    for (Player target2 : Bukkit.getOnlinePlayers()){
                        target2.showPlayer(target);
                    }
                }
                target.teleport(lobby);
                target.getInventory().clear();
            }
            for (Player target : Bukkit.getOnlinePlayers()){
                if (catchmeifyoucan.getSeekerList().contains(target.getUniqueId())){
                    catchmeifyoucan.getSeekerList().remove(target.getUniqueId());
                } else if (catchmeifyoucan.getHiderList().contains(target.getUniqueId())){
                    catchmeifyoucan.getHiderList().remove(target.getUniqueId());
                } else if (catchmeifyoucan.getSpectatorList().contains(target.getUniqueId())){
                    catchmeifyoucan.getSpectatorList().remove(target.getUniqueId());
                }
            }
            catchmeifyoucan.gameactive = false;
        } else {
            for (Player target : Bukkit.getOnlinePlayers()){
                target.sendMessage(p.getDisplayName() + ChatColor.BLUE + " ragequited! There are "  + ChatColor.GREEN + + catchmeifyoucan.getHiderList().size() + ChatColor.BLUE + " hiders left!");
            }
        }
    }
    @EventHandler
    public void painitngBreakd(HangingBreakByEntityEvent e){
        if (catchmeifyoucan.getIsGame()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick (PlayerInteractAtEntityEvent e) {
        if (!catchmeifyoucan.getIsGame()) {
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
    public void onPlayerInteract(PlayerInteractEvent event){
        if (!catchmeifyoucan.getIsGame()) {
            Player p = event.getPlayer();
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (!p.getPassengers().isEmpty()) {
                    Entity entity = p.getPassengers().get(0);
                    p.removePassenger(entity);
                    entity.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                }
            }
        }
    }
}
