package com.ja90n.catchmeifyoucan.commands;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MainCommand implements CommandExecutor {

    private CatchMeIfYouCan catchMeIfYouCan;

    public MainCommand(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command!");
        } else {
            Player player = (Player) sender;
            switch (args.length){
                case 0:
                    helpCommand(player);
                    break;
                case 1:
                    switch (args[0]){
                        case "leave":
                            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                                catchMeIfYouCan.getArenaManager().getArena(player).removePlayer(player);
                                player.sendMessage(ChatColor.RED + "You have left the game!");
                            } else {
                                player.sendMessage(ChatColor.RED + "You are not in a game!");
                            }
                            break;
                        case "list":
                            player.sendMessage(ChatColor.BLUE + "These are the available arenas:");
                            for (Arena arena : catchMeIfYouCan.getArenaManager().getArenas()){
                                player.sendMessage(ChatColor.BLUE + "- " + arena.getName() + " (" + arena.getId() + ")" + ChatColor.GRAY + "(Game state: " + arena.getGameState() + ")");
                            }
                            break;
                        case "start":
                            if (player.hasPermission("cmiyc.host")){
                                if (catchMeIfYouCan.getArenaManager().getArena(player) == null){
                                    player.sendMessage(ChatColor.RED + "You are not in a game!");
                                } else {
                                    catchMeIfYouCan.getArenaManager().getArena(player).startGame();
                                    player.sendMessage(ChatColor.BLUE + "Stated game!");
                                }
                            }
                            break;
                        case "stop":
                            if (player.hasPermission("cmiyc.host")){
                                if (catchMeIfYouCan.getArenaManager().getArena(player) == null){
                                    player.sendMessage(ChatColor.RED + "You are not in a game!");
                                } else {
                                    catchMeIfYouCan.getArenaManager().getArena(player).stopGame();
                                    player.sendMessage(ChatColor.BLUE + "Stopped game!");
                                }
                            }
                            break;
                        case "listplayers":
                            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                                for (UUID uuid : catchMeIfYouCan.getArenaManager().getArena(player).getPlayers()){
                                    player.sendMessage(Bukkit.getPlayer(uuid).getDisplayName());
                                }
                            }
                            break;
                        case "spawnpoint":
                            if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                                player.sendMessage("Location:");
                                player.sendMessage("X: " + catchMeIfYouCan.getConfig().getDouble("arenas." + catchMeIfYouCan.getArenaManager().getArena(player).getId() + "lobby-spawn.x"));
                                player.sendMessage("Y: " + catchMeIfYouCan.getConfig().getDouble("arenas." + catchMeIfYouCan.getArenaManager().getArena(player).getId() + "lobby-spawn.y"));
                                player.sendMessage("Z: " + catchMeIfYouCan.getConfig().getDouble("arenas." + catchMeIfYouCan.getArenaManager().getArena(player).getId() + "lobby-spawn.Z"));
                            }
                            break;
                        default:
                            helpCommand(player);
                            break;
                    }
                    break;
                case 2:
                    if (args[0].equals("join")){
                        if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
                            player.sendMessage(ChatColor.RED + "You are already in a arena!");
                        } else {
                            catchMeIfYouCan.getArenaManager().getArena(args[1]).addPlayer(player);
                            player.sendMessage(ChatColor.RED + "You have joined the game!");
                        }
                    } else {
                        helpCommand(player);
                        break;
                    }
            }
        }
        return false;
    }

    public void helpCommand(Player player){
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
        player.sendMessage(ChatColor.AQUA + "/cmiyc help: " + ChatColor.WHITE + "Gives you all the commands");
        player.sendMessage(ChatColor.RED + "/cmiyc join <arena>: " + ChatColor.WHITE + "Makes you join a game");
        player.sendMessage(ChatColor.BLUE + "/cmiyc leave: " + ChatColor.WHITE + "Makes you leave your game");
        player.sendMessage(ChatColor.YELLOW + "/cmiyc list: " + ChatColor.WHITE + "Lists all arenas");
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
    }
}
