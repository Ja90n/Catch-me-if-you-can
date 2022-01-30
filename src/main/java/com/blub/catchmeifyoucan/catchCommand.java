package com.blub.catchmeifyoucan;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class catchCommand implements CommandExecutor {


    private Catchmeifyoucan catchmeifyoucan;
    public catchCommand(Catchmeifyoucan catchmeifyoucan) {
        this.catchmeifyoucan = catchmeifyoucan;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You are not a player");
        } else {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!catchmeifyoucan.getIsGame()){
                        if (catchmeifyoucan.getSeekerList().isEmpty()){
                            p.sendMessage(ChatColor.RED + "There are not enough players to begin");
                        } else if (catchmeifyoucan.getHiderList().size() < 2) {
                            p.sendMessage(ChatColor.RED + "There are not enough players to begin");
                        } else {
                            double sx = catchmeifyoucan.getConfig().getDouble("seekerspawn.x");
                            double sy = catchmeifyoucan.getConfig().getDouble("seekerspawn.y");
                            double sz = catchmeifyoucan.getConfig().getDouble("seekerspawn.z");
                            Location seekerspawn = new Location(Bukkit.getWorld("hideandseek-city"), sx, sy, sz, 0, 0);
                            double hx = catchmeifyoucan.getConfig().getDouble("hiderspawn.x");
                            double hy = catchmeifyoucan.getConfig().getDouble("hiderspawn.y");
                            double hz = catchmeifyoucan.getConfig().getDouble("hiderspawn.z");
                            Location hiderspawn = new Location(Bukkit.getWorld("hideandseek-city"), hx, hy, hz, 0, 0);
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                if (catchmeifyoucan.getSeekerList().contains(onlinePlayer.getUniqueId())) {
                                    onlinePlayer.teleport(seekerspawn);
                                    onlinePlayer.sendMessage(ChatColor.GREEN + "The game has been started, you have to wait 45 seconds to begin");
                                    onlinePlayer.setGameMode(GameMode.ADVENTURE);
                                    //give the seeker potioneffects
                                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 900 ,10, false, true));
                                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 900 ,100, false, true));
                                    //set the seeker inventory
                                    onlinePlayer.getInventory().clear();
                                    onlinePlayer.getInventory().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                                    onlinePlayer.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                                    onlinePlayer.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                    onlinePlayer.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    onlinePlayer.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));

                                } else if (catchmeifyoucan.getHiderList().contains(onlinePlayer.getUniqueId())) {
                                    onlinePlayer.teleport(hiderspawn);
                                    onlinePlayer.sendMessage(ChatColor.GREEN + "The game has been started, go and do not get caught, you have 45 seconds to hide!");
                                    onlinePlayer.setGameMode(GameMode.ADVENTURE);
                                    onlinePlayer.getInventory().clear();
                                    ItemStack item = new ItemStack(Material.LEGACY_SKULL, 1 , (short) 3);
                                    SkullMeta meta = (SkullMeta) item.getItemMeta();
                                    meta.setOwner(onlinePlayer.getDisplayName());
                                    item.setItemMeta(meta);
                                    onlinePlayer.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                    onlinePlayer.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                                    onlinePlayer.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                                    onlinePlayer.getInventory().setHelmet(item);
                                    onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999 , 1, false, false));
                                } else if (catchmeifyoucan.getSpectatorList().contains(onlinePlayer.getUniqueId())){
                                    double spx = catchmeifyoucan.getConfig().getDouble("spectatorspawn.x");
                                    double spy = catchmeifyoucan.getConfig().getDouble("spectatorspawn.y");
                                    double spz = catchmeifyoucan.getConfig().getDouble("spectatorspawn.z");
                                    Location spectatorspawn = new Location(Bukkit.getWorld("hideandseek-city"), spx, spy, spz, 0, 0);
                                    onlinePlayer.teleport(spectatorspawn);
                                    onlinePlayer.setGameMode(GameMode.ADVENTURE);
                                    onlinePlayer.setFlying(true);
                                    onlinePlayer.setAllowFlight(true);
                                    for (Player target : Bukkit.getOnlinePlayers()){
                                        if (!catchmeifyoucan.getSpectatorList().contains(target.getUniqueId())){
                                            if (catchmeifyoucan.getSeekerList().contains(target.getUniqueId())){
                                                target.hidePlayer(onlinePlayer);
                                            } else if (catchmeifyoucan.getHiderList().contains(target.getUniqueId())){
                                                target.hidePlayer(onlinePlayer);
                                            }
                                        }
                                    }
                                }
                                catchmeifyoucan.gameactive = true;
                            }
                            p.sendMessage(ChatColor.BLUE + "You have started the game!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "The game is still active");
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (catchmeifyoucan.getIsGame()){
                        Player winner = Bukkit.getPlayer(catchmeifyoucan.getHiderList().get(0));
                        double sx = catchmeifyoucan.getConfig().getDouble("lobby.x");
                        double sy = catchmeifyoucan.getConfig().getDouble("lobby.y");
                        double sz = catchmeifyoucan.getConfig().getDouble("lobby.z");
                        Location lobby = new Location(p.getWorld(), sx, sy, sz, 0, 0);
                        for (Player target : Bukkit.getOnlinePlayers()){
                            target.sendMessage(ChatColor.RED + "The game has been stopped!");
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
                        p.sendMessage(ChatColor.BLUE + "You have stopped the game!");
                    } else {
                        p.sendMessage(ChatColor.RED + "There is no game active");
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    //Let the players leave their teams
                    if (catchmeifyoucan.getSeekerList().contains(p.getUniqueId())){
                        catchmeifyoucan.getSeekerList().remove(p.getUniqueId());
                        p.sendMessage(ChatColor.RED + "You have left the seekers!");
                        p.setHealth(0);
                    } else if (catchmeifyoucan.getHiderList().contains(p.getUniqueId())){
                        catchmeifyoucan.getHiderList().remove(p.getUniqueId());
                        p.sendMessage(ChatColor.RED + "You have left the hiders!");
                        p.setHealth(0);
                    } else if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())) {
                        catchmeifyoucan.getSpectatorList().remove(p.getUniqueId());
                        p.sendMessage(ChatColor.RED + "You have left the spectators!");
                        p.setHealth(0);
                    } else{
                        p.sendMessage(ChatColor.RED + "You are not in a team");
                    }

                } else if (args[0].equalsIgnoreCase("list")) {
                    if (!catchmeifyoucan.getSeekerList().isEmpty()){
                        p.sendMessage(ChatColor.RED + "There are " + ChatColor.WHITE + catchmeifyoucan.getSeekerList().size() + ChatColor.RED + " seekers!");
                    }
                    if (!catchmeifyoucan.getHiderList().isEmpty()){
                        p.sendMessage(ChatColor.RED + "There are " + ChatColor.WHITE + catchmeifyoucan.getHiderList().size() + ChatColor.RED + " hiders!");
                    }
                    if (!catchmeifyoucan.getSpectatorList().isEmpty()){
                        p.sendMessage(ChatColor.RED + "There are " + ChatColor.WHITE + catchmeifyoucan.getSpectatorList().size() + ChatColor.RED + " spectators!");
                    }
                } else if (args[0].equalsIgnoreCase("help")){
                    sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
                    sender.sendMessage(ChatColor.RED + "/catch join seeker: " + ChatColor.WHITE + "Makes you a seeker");
                    sender.sendMessage(ChatColor.BLUE + "/catch join hider: " + ChatColor.WHITE + "Makes you a hider");
                    sender.sendMessage(ChatColor.YELLOW + "/catch leave: " + ChatColor.WHITE + "Makes you leave the game");
                    sender.sendMessage(ChatColor.GREEN + "/catch start: " + ChatColor.WHITE + "Makes you start the game");
                    sender.sendMessage(ChatColor.GOLD + "/catch stop: " + ChatColor.WHITE + "Makes you stop the game");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "/catch list: " + ChatColor.WHITE + "Lists all the players");
                    sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");

                } else if (args[0].equalsIgnoreCase("reload")){
                    if (!catchmeifyoucan.getIsGame()){
                        catchmeifyoucan.reloadConfig();
                        p.sendMessage(ChatColor.GREEN + "Config has been reloaded!");
                    } else {
                        p.sendMessage(ChatColor.RED + "The game is still active");
                    }
                } else {
                    sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
                    sender.sendMessage(ChatColor.RED + "/catch join seeker: " + ChatColor.WHITE + "Makes you a seeker");
                    sender.sendMessage(ChatColor.BLUE + "/catch join hider: " + ChatColor.WHITE + "Makes you a hider");
                    sender.sendMessage(ChatColor.YELLOW + "/catch leave: " + ChatColor.WHITE + "Makes you leave the game");
                    sender.sendMessage(ChatColor.GREEN + "/catch start: " + ChatColor.WHITE + "Makes you start the game");
                    sender.sendMessage(ChatColor.GOLD + "/catch stop: " + ChatColor.WHITE + "Makes you stop the game");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "/catch list: " + ChatColor.WHITE + "Lists all the players");
                    sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
                }
            } else if (args.length == 2) {
                //Letting players join teams logic
                if (args[0].equalsIgnoreCase("join")) {
                    if (!catchmeifyoucan.getIsGame()){
                        if (args[1].equalsIgnoreCase("seeker")) {
                            if (catchmeifyoucan.getSeekerList().contains(p.getUniqueId())){
                                p.sendMessage(ChatColor.RED + "You are already a seeker!");
                            } else if (catchmeifyoucan.getHiderList().contains(p.getUniqueId())){
                                catchmeifyoucan.getHiderList().remove(p.getUniqueId());
                                catchmeifyoucan.getSeekerList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have switched to a seeker!");
                            } else if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())){
                                catchmeifyoucan.getSpectatorList().remove(p.getUniqueId());
                                catchmeifyoucan.getSeekerList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have switched to a seeker!");
                            } else {
                                catchmeifyoucan.getSeekerList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have become a seeker!");
                            }
                        } else if (args[1].equalsIgnoreCase("hider")) {
                            if (catchmeifyoucan.getSeekerList().contains(p.getUniqueId())){
                                catchmeifyoucan.getSeekerList().remove(p.getUniqueId());
                                catchmeifyoucan.getHiderList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have switched to a hider!");
                            } else if (catchmeifyoucan.getHiderList().contains(p.getUniqueId())){
                                p.sendMessage(ChatColor.RED + "You are already a hider!");
                            } else if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())){
                                catchmeifyoucan.getSpectatorList().remove(p.getUniqueId());
                                catchmeifyoucan.getHiderList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have switched to a hider!");
                            } else {
                                catchmeifyoucan.getHiderList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have become a hider!");
                            }
                        } else if (args[1].equalsIgnoreCase("spectator")){
                            if (catchmeifyoucan.getSeekerList().contains(p.getUniqueId())){
                                catchmeifyoucan.getSeekerList().remove(p.getUniqueId());
                                catchmeifyoucan.getSpectatorList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have switched to a spectator!");
                            } else if (catchmeifyoucan.getHiderList().contains(p.getUniqueId())){
                                catchmeifyoucan.getHiderList().remove(p.getUniqueId());
                                catchmeifyoucan.getSpectatorList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have switched to a spectator!");
                            } else if (catchmeifyoucan.getSpectatorList().contains(p.getUniqueId())){
                                p.sendMessage(ChatColor.RED + "You are already a spectator!");
                            } else {
                                catchmeifyoucan.getSpectatorList().add(p.getUniqueId());
                                p.sendMessage(ChatColor.GREEN + "You have become a spectator!");
                            }
                    } else {
                        p.sendMessage(ChatColor.RED + "The game is still active");
                    }

                    } else {
                        sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
                        sender.sendMessage(ChatColor.RED + "/catch join seeker: " + ChatColor.WHITE + "Makes you a seeker");
                        sender.sendMessage(ChatColor.BLUE + "/catch join hider: " + ChatColor.WHITE + "Makes you a hider");
                        sender.sendMessage(ChatColor.YELLOW + "/catch leave: " + ChatColor.WHITE + "Makes you leave the game");
                        sender.sendMessage(ChatColor.GREEN + "/catch start: " + ChatColor.WHITE + "Makes you start the game");
                        sender.sendMessage(ChatColor.GOLD + "/catch stop: " + ChatColor.WHITE + "Makes you stop the game");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/catch list: " + ChatColor.WHITE + "Lists all the players");
                        sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");

                    }
                } else {
                    sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
                    sender.sendMessage(ChatColor.RED + "/catch join seeker: " + ChatColor.WHITE + "Makes you a seeker");
                    sender.sendMessage(ChatColor.BLUE + "/catch join hider: " + ChatColor.WHITE + "Makes you a hider");
                    sender.sendMessage(ChatColor.YELLOW + "/catch leave: " + ChatColor.WHITE + "Makes you leave the game");
                    sender.sendMessage(ChatColor.GREEN + "/catch start: " + ChatColor.WHITE + "Makes you start the game");
                    sender.sendMessage(ChatColor.GOLD + "/catch stop: " + ChatColor.WHITE + "Makes you stop the game");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "/catch list: " + ChatColor.WHITE + "Lists all the players");
                    sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");

                }
            } else {
                sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
                sender.sendMessage(ChatColor.RED + "/catch join seeker: " + ChatColor.WHITE + "Makes you a seeker");
                sender.sendMessage(ChatColor.BLUE + "/catch join hider: " + ChatColor.WHITE + "Makes you a hider");
                sender.sendMessage(ChatColor.YELLOW + "/catch leave: " + ChatColor.WHITE + "Makes you leave the game");
                sender.sendMessage(ChatColor.GREEN + "/catch start: " + ChatColor.WHITE + "Makes you start the game");
                sender.sendMessage(ChatColor.GOLD + "/catch stop: " + ChatColor.WHITE + "Makes you stop the game");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "/catch list: " + ChatColor.WHITE + "Lists all the players");
                sender.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");

            }
        }
        return false;
    }
}
