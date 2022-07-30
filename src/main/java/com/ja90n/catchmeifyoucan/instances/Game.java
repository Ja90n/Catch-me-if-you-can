package com.ja90n.catchmeifyoucan.instances;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.runnables.SeekerStartCountdownRunnable;
import com.ja90n.catchmeifyoucan.runnables.ShowHidersRunnable;
import com.ja90n.catchmeifyoucan.utils.SetupPlayerUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private CatchMeIfYouCan catchMeIfYouCan;
    private Arena arena;
    private HashMap<UUID,String> teams;
    private List<UUID> seekerCountdown;
    private ShowHidersRunnable showHidersRunnable;

    public Game(CatchMeIfYouCan catchMeIfYouCan, Arena arena){
        showHidersRunnable = new ShowHidersRunnable(catchMeIfYouCan,arena);
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.arena = arena;
        seekerCountdown = new ArrayList<>();
        teams = new HashMap<>();
    }

    public void start() {
        int random = ThreadLocalRandom.current().nextInt(0,arena.getPlayers().size());
        for (UUID uuid : arena.getPlayers()){
            Player player = Bukkit.getPlayer(uuid);
            player.removePotionEffect(PotionEffectType.SPEED);
            if (arena.getPlayers().indexOf(uuid) == random){
                teams.put(uuid,"seeker");
                new SetupPlayerUtil(player,"seeker");
                new SeekerStartCountdownRunnable(catchMeIfYouCan,player,arena);
            } else {
                teams.put(uuid,"hider");
                new SetupPlayerUtil(player,"hider");
                player.teleport(catchMeIfYouCan.getConfigManager().getHiderSpawn(arena.getId()));
                player.sendMessage(ChatColor.BLUE + "You are a runner!");
                player.sendMessage(ChatColor.BLUE + "Game has been started! You have " + catchMeIfYouCan.getConfigManager().getSeekerWaitTime()  + " seconds to run away!");
                player.sendTitle(ChatColor.BLUE + "Game has been started!", ChatColor.GRAY + "You have " + catchMeIfYouCan.getConfigManager().getSeekerWaitTime()  + " seconds to run away!");
            }
            player.setHealth(20);
            player.setGameMode(GameMode.ADVENTURE);
        }
        arena.getWorld().setTime(0);
        arena.getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        arena.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);
        arena.getRunnerWinCountdownRunnable().start();
        arena.setGameState(GameState.LIVE);
    }

    public void toggleRunnableCheck(Player player, boolean bool){
        if (bool){
            seekerCountdown.add(player.getUniqueId());
        } else {
            seekerCountdown.remove(player.getUniqueId());
        }
    }

    public HashMap<UUID, String> getTeams() {
        return teams;
    }

    public List<UUID> getSeekerCountdown() {
        return seekerCountdown;
    }

    public List<UUID> getHiders() {
        List<UUID> hiders = new ArrayList<>();
        for (UUID uuid : teams.keySet()){
            if (teams.get(uuid).equals("hider")){
                hiders.add(uuid);
            }
        }
        return hiders;
    }

    public List<UUID> getSeekers() {
        List<UUID> hiders = new ArrayList<>();
        for (UUID uuid : teams.keySet()){
            if (teams.get(uuid).equals("seeker")){
                hiders.add(uuid);
            }
        }
        return hiders;
    }

    public ShowHidersRunnable getShowHidersRunnable() {
        return showHidersRunnable;
    }
}