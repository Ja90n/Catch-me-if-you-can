package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.instances.Arena;
import com.ja90n.catchmeifyoucan.runnables.InstantRespawnRunnable;
import com.ja90n.catchmeifyoucan.utils.SetupPlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class PlayerDeath implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PlayerDeath(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        event.getDrops().clear();
        event.setDroppedExp(0);
        if (catchMeIfYouCan.getArenaManager().getArena(player) != null) {
            Arena arena = catchMeIfYouCan.getArenaManager().getArena(player);
            if (arena.getGameState().equals(GameState.LIVE)) {
                if (arena.getGame().getTeams().get(player.getUniqueId()).equals("seeker")){
                    event.setDeathMessage(ChatColor.RED + "Catcher " + player.getDisplayName() + " has died!");
                } else if (arena.getGame().getTeams().get(player.getUniqueId()).equals("hider")) {
                    arena.getGame().getTeams().remove(player.getUniqueId());
                    arena.getGame().getTeams().put(player.getUniqueId(),"spectator");
                    int hinderAmount = 0;
                    for (UUID uuid : arena.getGame().getTeams().keySet()){
                        if (arena.getGame().getTeams().get(uuid).equals("hider")){
                            hinderAmount++;
                        }
                    }
                    if (hinderAmount <= 0){
                        arena.sendMessage(ChatColor.DARK_RED + "The catchers has won the game!");
                        arena.sendTitle(ChatColor.DARK_RED + "The catchers has won the game!",ChatColor.GRAY + "Thank you for playing!");
                        arena.stopGame();
                    } else {
                        if (player.getKiller() == null){
                            event.setDeathMessage(ChatColor.BLUE + "Runner " + ChatColor.WHITE + player.getDisplayName() + ChatColor.BLUE + " has died!");
                        } else {
                            event.setDeathMessage(ChatColor.BLUE + "Runner " + ChatColor.WHITE + player.getDisplayName() + ChatColor.BLUE + " was killed by " + ChatColor.WHITE + player.getKiller().getDisplayName() + ChatColor.BLUE + "!");
                        }
                    }
                }
            }
        }
        new InstantRespawnRunnable(catchMeIfYouCan,event.getEntity());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
        if (catchMeIfYouCan.getArenaManager().getArena(player) != null){
            Arena arena = catchMeIfYouCan.getArenaManager().getArena(player);
            if (arena.getGameState().equals(GameState.LIVE)){
                if (arena.getGame().getTeams().get(player.getUniqueId()).equals("seeker")){
                    new SetupPlayerUtil(player,"seeker");
                    event.setRespawnLocation(catchMeIfYouCan.getConfigManager().getSeekerSpawn(arena.getId()));
                } else if (arena.getGame().getTeams().get(player.getUniqueId()).equals("spectator")){
                    event.setRespawnLocation(event.getPlayer().getLocation());
                    player.setInvisible(true);
                    player.setInvulnerable(true);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                } else {
                    event.setRespawnLocation(catchMeIfYouCan.getConfigManager().getLobbySpawn(catchMeIfYouCan.getArenaManager().getArena(player).getId()));
                }
            } else {
                event.setRespawnLocation(catchMeIfYouCan.getConfigManager().getLobbySpawn(catchMeIfYouCan.getArenaManager().getArena(player).getId()));
            }
        }
    }
}
