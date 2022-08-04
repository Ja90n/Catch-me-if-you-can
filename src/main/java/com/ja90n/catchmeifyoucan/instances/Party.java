package com.ja90n.catchmeifyoucan.instances;

import com.ja90n.catchmeifyoucan.managers.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    private UUID partyLeader;
    private PartyManager partyManager;

    private List<UUID> players;

    public Party(Player leader, PartyManager partyManager){
        this.partyManager = partyManager;
        partyLeader = leader.getUniqueId();
        players = new ArrayList<>();
        players.add(leader.getUniqueId());
    }

    public void joinGame(Arena arena){
        for (UUID uuid : players){
            arena.addPlayer(Bukkit.getPlayer(uuid));
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.BLUE + "The party leader has joined the game!");
        }
    }

    public void leaveGame(Arena arena){
        for (UUID uuid : players){
            arena.removePlayer(Bukkit.getPlayer(uuid));
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.BLUE + "The party leader has left the game!");
        }
    }

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public UUID getPartyLeader() {
        return partyLeader;
    }

    public void setPartyLeader(UUID partyLeader) {
        this.partyLeader = partyLeader;
    }
}
