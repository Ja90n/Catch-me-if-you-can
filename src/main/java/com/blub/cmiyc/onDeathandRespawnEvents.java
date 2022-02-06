package com.blub.cmiyc;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class onDeathandRespawnEvents implements Listener {

    private Cmiyc cmiyc;
    public onDeathandRespawnEvents(Cmiyc cmiyc) {
        this.cmiyc = cmiyc;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if (cmiyc.issGameActive()) {
            Player p = e.getEntity().getPlayer();
            if (cmiyc.getSeekerplayers().contains(p.getUniqueId())) {
                e.setDeathMessage(ChatColor.BLUE + "Seeker " + ChatColor.WHITE + p.getDisplayName() + ChatColor.BLUE + " has died!");
            } else if (cmiyc.getHiderplayers().contains(p.getUniqueId())) {
                cmiyc.getHiderplayers().remove(p.getUniqueId());
                cmiyc.getSpectatorplayers().add(p.getUniqueId());
                if (cmiyc.getHiderplayers().size() == 1) {
                    //Stopping the game
                    Player winner = Bukkit.getPlayer(cmiyc.getHiderplayers().get(0));
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (cmiyc.getHiderplayers().contains(target.getUniqueId())) {
                            cmiyc.getHiderplayers().remove(target.getUniqueId());
                            target.getInventory().clear();
                        } else if (cmiyc.getSeekerplayers().contains(target.getUniqueId())) {
                            cmiyc.getSeekerplayers().remove(target.getUniqueId());
                            target.getInventory().clear();
                        } else if (cmiyc.getSpectatorplayers().contains(target.getUniqueId())) {
                            cmiyc.getSpectatorplayers().remove(target.getUniqueId());
                            target.getInventory().clear();
                        }

                        //Removing effects
                        for (Player target2 : Bukkit.getOnlinePlayers()) {
                            target.showPlayer(target2);
                            target.removePotionEffect(PotionEffectType.INVISIBILITY);
                            target.removePotionEffect(PotionEffectType.SPEED);
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            target.getInventory().clear();
                        }

                        //Teleporting player
                        double hx = cmiyc.getConfig().getDouble("lobby.x");
                        double hy = cmiyc.getConfig().getDouble("lobby.y");
                        double hz = cmiyc.getConfig().getDouble("lobby.z");
                        Location lobby = new Location(Bukkit.getWorld(cmiyc.getConfig().getString("world")), hx, hy, hz, 0, 0);
                        target.teleport(lobby);

                        //Sending message
                        target.sendMessage(winner.getDisplayName() + ChatColor.BLUE + " has won the game!");
                    }
                    cmiyc.isgameactive = false;
                } else {
                    e.setDeathMessage(ChatColor.BLUE + "Hider " + ChatColor.WHITE + p.getDisplayName() + ChatColor.BLUE + " has died, there are " + ChatColor.WHITE + cmiyc.getHiderplayers().size() + ChatColor.BLUE + " hiders remaining!");
                }
            } else if (cmiyc.getSpectatorplayers().contains(p.getUniqueId())) {
                p.sendMessage("How did you do this?");
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (cmiyc.issGameActive()) {
            Player p = e.getPlayer();
            if (cmiyc.getSpectatorplayers().contains(p.getUniqueId())) {

                //Setting spectator spawn
                double sx = cmiyc.getConfig().getDouble("spectatorspawn.x");
                double sy = cmiyc.getConfig().getDouble("spectatorspawn.y");
                double sz = cmiyc.getConfig().getDouble("spectatorspawn.z");
                Location spectatorspawn = new Location(Bukkit.getWorld("world"), sx, sy, sz, 0, 0);
                e.setRespawnLocation(spectatorspawn);

                //Setting the player
                p.setAllowFlight(true);
                p.setFlying(true);
                p.setGameMode(GameMode.ADVENTURE);
                ItemStack compas = new ItemStack(Material.COMPASS);
                ItemMeta compasmeta = compas.getItemMeta();
                compasmeta.setDisplayName(ChatColor.WHITE + "Spectator compass");

                //Vanish the spectator
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (cmiyc.getSpectatorplayers().contains(target.getUniqueId())) {
                        p.showPlayer(target);
                    } else {
                        target.hidePlayer(p);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if (cmiyc.issGameActive()){
            Player p = e.getPlayer();

            //Removing the player from the game
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (cmiyc.getHiderplayers().contains(target.getUniqueId())) {
                    cmiyc.getHiderplayers().remove(target.getUniqueId());
                    target.getInventory().clear();
                } else if (cmiyc.getSeekerplayers().contains(target.getUniqueId())) {
                    cmiyc.getSeekerplayers().remove(target.getUniqueId());
                    target.getInventory().clear();
                } else if (cmiyc.getSpectatorplayers().contains(target.getUniqueId())) {
                    cmiyc.getSpectatorplayers().remove(target.getUniqueId());
                    target.getInventory().clear();
                }
            }

            if (cmiyc.getHiderplayers().size() == 1 || cmiyc.getSeekerplayers().size() == 0){
                //Stopping the game
                Player winner = Bukkit.getPlayer(cmiyc.getHiderplayers().get(0));
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (cmiyc.getHiderplayers().contains(target.getUniqueId())) {
                        cmiyc.getHiderplayers().remove(target.getUniqueId());
                        target.getInventory().clear();
                    } else if (cmiyc.getSeekerplayers().contains(target.getUniqueId())) {
                        cmiyc.getSeekerplayers().remove(target.getUniqueId());
                        target.getInventory().clear();
                    } else if (cmiyc.getSpectatorplayers().contains(target.getUniqueId())) {
                        cmiyc.getSpectatorplayers().remove(target.getUniqueId());
                        target.getInventory().clear();
                    }

                    //Removing effects
                    for (Player target2 : Bukkit.getOnlinePlayers()) {
                        target.showPlayer(target2);
                        target.removePotionEffect(PotionEffectType.INVISIBILITY);
                        target.removePotionEffect(PotionEffectType.SPEED);
                        target.setAllowFlight(false);
                        target.setFlying(false);
                        target.getInventory().clear();
                    }

                    //Teleporting player
                    double hx = cmiyc.getConfig().getDouble("lobby.x");
                    double hy = cmiyc.getConfig().getDouble("lobby.y");
                    double hz = cmiyc.getConfig().getDouble("lobby.z");
                    Location lobby = new Location(Bukkit.getWorld(cmiyc.getConfig().getString("world")), hx, hy, hz, 0, 0);
                    target.teleport(lobby);

                    //Sending message
                    target.sendMessage(winner.getDisplayName() + ChatColor.BLUE + " has won the game because of a ragequit!");
                    cmiyc.isgameactive = false;
                }
            } else {
                for (Player target : Bukkit.getOnlinePlayers()){
                    target.sendMessage(ChatColor.BLUE + "Player " + ChatColor.WHITE + p.getDisplayName() + ChatColor.BLUE + " has left, there are " + ChatColor.WHITE + cmiyc.getHiderplayers().size() + ChatColor.BLUE + " hiders remaining!");
                }
            }
        }
    }
}
