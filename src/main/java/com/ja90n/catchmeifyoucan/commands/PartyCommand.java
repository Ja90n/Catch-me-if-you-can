package com.ja90n.catchmeifyoucan.commands;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Party;
import com.ja90n.catchmeifyoucan.managers.PartyManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.UUID;

public class PartyCommand implements CommandExecutor {

    private CatchMeIfYouCan catchMeIfYouCan;
    private PartyManager partyManager;

    public PartyCommand(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.partyManager = catchMeIfYouCan.getPartyManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            switch (args.length){
                case 0:
                    helpCommand(player);
                    break;
                case 1:
                    switch (args[0]){
                        case "leave":
                            partyManager.removePlayer(player);
                            player.sendMessage(ChatColor.BLUE + "You have left the party!");
                            break;
                        case "list":
                            partyManager.listParty(player);
                            break;
                        case "disband":
                            if (partyManager.getParty(player) != null){
                                if (partyManager.getParty(player).getPartyLeader().equals(player.getUniqueId())){
                                    partyManager.disbandParty(partyManager.getParty(player));
                                    player.sendMessage(ChatColor.BLUE + "You disbanded the party!");
                                } else {
                                    player.sendMessage(ChatColor.RED + "You are not the party leader!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You are not in a party!");
                            }
                            break;
                        case "create":
                            if (partyManager.getParty(player) == null){
                                partyManager.newParty(player);
                                player.sendMessage(ChatColor.BLUE + "You have created a new party!");
                            } else {
                                player.sendMessage(ChatColor.RED + "You are already in a party!");
                            }
                            break;
                        case "listp":
                            for (UUID uuid : partyManager.getPlayers()){
                                player.sendMessage(Bukkit.getPlayer(uuid).getDisplayName());
                            }
                            break;
                        default:
                            helpCommand(player);
                            break;
                    }
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("join")){
                        if (partyManager.getParty(player) == null){
                            for (Party party : partyManager.getParties()){
                                for (UUID uuid : party.getPlayers()){
                                    if (args[1].equalsIgnoreCase(Bukkit.getPlayer(uuid).getDisplayName())){
                                        party.addPlayer(player);
                                        partyManager.sendMessage(party,ChatColor.BLUE + "Player " + ChatColor.WHITE + player.getDisplayName() + ChatColor.BLUE + " has been added to the party!");
                                        return false;
                                    }
                                }
                            }
                            player.sendMessage(ChatColor.RED + "The party you are trying to join does not exist!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You are already in a party!");
                        }
                    } else if (args[0].equalsIgnoreCase("invite")){
                        if (partyManager.getParty(player) != null){
                            if (!args[1].equals(player.getDisplayName())){
                                for (Player target : Bukkit.getOnlinePlayers()){
                                    if (args[1].equals(target.getDisplayName())){
                                        target.sendMessage(ChatColor.BLUE + "You have been invited to " + ChatColor.WHITE +
                                                Bukkit.getPlayer(partyManager.getParty(player).getPartyLeader()).getDisplayName() +
                                                "'s " + ChatColor.BLUE + " party by " + ChatColor.WHITE + player.getDisplayName()
                                                + ChatColor.BLUE + "!");
                                        TextComponent textComponent = new TextComponent("§9Click ");
                                        TextComponent clickHere = new TextComponent("§eHERE");
                                        TextComponent textComponent2 = new TextComponent(" §9to join §f" + player.getDisplayName() + "'s §9party!");
                                        clickHere.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/party join " + player.getDisplayName()));
                                        clickHere.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§9Join the party of §f" + player.getDisplayName())));
                                        target.spigot().sendMessage(new BaseComponent[]{textComponent,clickHere,textComponent2});
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You can't invite yourself!");
                            }
                            player.sendMessage(ChatColor.RED + "There is no player online with that name!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not in a party!");
                        }
                    } else if (args[0].equalsIgnoreCase("promote")) {
                        if (partyManager.getParty(player) != null){
                            if (partyManager.getParty(player).getPartyLeader().equals(player.getUniqueId())){
                                if (partyManager.getParty(player).getPlayers().contains(Bukkit.getPlayer(args[1]).getUniqueId())){
                                    partyManager.getParty(player).setPartyLeader(Bukkit.getPlayer(args[1]).getUniqueId());
                                } else {
                                    player.sendMessage(ChatColor.RED + "That player is not in your party!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You are not the party leader of your party!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not in a party!");
                        }
                    } else {
                        helpCommand(player);
                    }
                    break;
                default:
                    helpCommand(player);
                    break;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command!");
        }
        return false;
    }

    public void helpCommand(Player player){
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
        player.sendMessage(ChatColor.RED + "/party join <player name>: " + ChatColor.WHITE + "Makes you join a party");
        player.sendMessage(ChatColor.GREEN + "/party leave: " + ChatColor.WHITE + "Makes you leave your party");
        player.sendMessage(ChatColor.YELLOW + "/party list: " + ChatColor.WHITE + "Lists all players in your party");
        player.sendMessage(ChatColor.AQUA + "/party help: " + ChatColor.WHITE + "Gives you all the commands");
        player.sendMessage(ChatColor.BLUE + "/party disband: " + ChatColor.WHITE + "Makes you disband your party");
        player.sendMessage(ChatColor.WHITE + "-----------------" + ChatColor.LIGHT_PURPLE + "=+=" + ChatColor.WHITE + "-----------------");
    }
}
