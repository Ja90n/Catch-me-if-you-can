package com.blub.catchmeifyoucan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;

public class catchEvents implements Listener {

    private Catchmeifyoucan catchmeifyoucan;

    public catchEvents(Catchmeifyoucan catchmeifyoucan) {
        this.catchmeifyoucan = catchmeifyoucan;
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e){
        if (catchmeifyoucan.getIsGame()){
            Player p = e.getEntity().getPlayer();
            e.setKeepInventory(true);
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
                     Location lobby = new Location(p.getWorld(), sx, sy, sz, 0, 0);
                     for (Player target : Bukkit.getOnlinePlayers()){
                         target.sendMessage(winner.getDisplayName() + ChatColor.BLUE + " has won the game!");
                         target.removePotionEffect(PotionEffectType.INVISIBILITY);
                         if (catchmeifyoucan.getSpectatorList().contains(target.getUniqueId())){
                             target.setFlying(false);
                             target.setAllowFlight(false);
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
                     e.setDeathMessage(p.getDisplayName() + ChatColor.GREEN + "got caught! There are "  + ChatColor.WHITE + + catchmeifyoucan.getHiderList().size() + ChatColor.GREEN + " hiders left!");
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
                Location spectatorspawn = new Location(p.getWorld(), sx, sy, sz, 0, 0);
                e.setRespawnLocation(spectatorspawn);
                Player target = Bukkit.getPlayer(catchmeifyoucan.getSpectatorList().get(0));
                e.setRespawnLocation(target.getLocation());
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
            Location lobby = new Location(Bukkit.getWorld("hideandseek-city"), sx, sy, sz, 0, 0);
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
}
