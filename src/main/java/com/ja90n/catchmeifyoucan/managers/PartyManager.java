package com.ja90n.catchmeifyoucan.managers;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    private CatchMeIfYouCan catchMeIfYouCan;
    private List<Party> parties;
    private List<UUID> players;

    public PartyManager(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
        parties = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void newParty(Player leader){
        Party party = new Party(leader,this);
        parties.add(party);
        players.add(leader.getUniqueId());
    }

    public void addPlayer(Player player,String playerName){
        if (!players.contains(player.getUniqueId())){
            if (getParty(Bukkit.getPlayer(playerName)) != null){
                getParty(Bukkit.getPlayer(playerName)).addPlayer(player);
                players.add(player.getUniqueId());
                player.sendMessage(ChatColor.BLUE + "You have been added to the party of " + ChatColor.WHITE + playerName + ChatColor.BLUE + "!");
            } else {
                player.sendMessage(ChatColor.RED + "Party you tried to join does not exist!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are already in a party");
        }
    }

    public void removePlayer(Player player){
        if (getParty(player) != null){
            Party party = getParty(player);
            party.removePlayer(player);
            if (party.getPlayers().isEmpty()){
                disbandParty(party);
            } else {
                sendMessage(party,ChatColor.RED + player.getDisplayName() + " has left the party!");
                if (party.getPartyLeader().equals(player.getUniqueId())){
                    changeLeader(player,party);
                }
            }
            players.remove(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a party!");
        }
    }

    public void changeLeader(Player player, Party party){
        party.setPartyLeader(player.getUniqueId());
        sendMessage(party,ChatColor.BLUE + Bukkit.getPlayer(party.getPlayers().get(0)).getDisplayName() + " has been promoted to party leader");
    }

    public void disbandParty(Party party){
        for (UUID uuid : party.getPlayers()){
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "Party was disbanded");
            removePlayer(Bukkit.getPlayer(uuid));
        }
        party.getPlayers().clear();
        parties.remove(party);
    }

    public void sendMessage(Party party, String message){
        for (UUID uuid : party.getPlayers()){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void listParty(Player player){
        if (getParty(player) != null){
            Party party = getParty(player);
            player.sendMessage(ChatColor.BLUE + "These are the players in your party: ");
            player.sendMessage(ChatColor.BLUE + "- " + ChatColor.GOLD + ChatColor.BOLD + Bukkit.getPlayer(party.getPartyLeader()).getDisplayName());
            for (UUID uuid : party.getPlayers()){
                if (!uuid.equals(party.getPartyLeader())){
                    player.sendMessage(ChatColor.BLUE + "- " + ChatColor.WHITE + Bukkit.getPlayer(uuid).getDisplayName());
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a party!");
        }
    }
    public Party getParty(Player player){
        for (Party party : parties){
            if (party.getPlayers().contains(player.getUniqueId())){
                return party;
            }
        }
        return null;
    }

    public List<Party> getParties() {
        return parties;
    }

    public List<UUID> getPlayers() {
        return players;
    }
}
