package com.ja90n.catchmeifyoucan.instances;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.runnables.RunnerWinCountdownRunnable;
import com.ja90n.catchmeifyoucan.runnables.StartCountdownRunnable;
import com.ja90n.catchmeifyoucan.utils.ReloadScoreboard;
import com.ja90n.catchmeifyoucan.utils.SetScoreboard;
import com.ja90n.catchmeifyoucan.utils.WinCheckUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private CatchMeIfYouCan catchMeIfYouCan;
    private int id;
    private String name;
    private List<UUID> players;
    private Game game;
    private GameState gameState;
    private StartCountdownRunnable startCountdownRunnable;

    public Arena(CatchMeIfYouCan catchMeIfYouCan, int id, String name) {
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.id = id;
        this.name = name;
        this.game = new Game(catchMeIfYouCan,this);
        this.gameState = GameState.RECRUITING;
        this.startCountdownRunnable = new StartCountdownRunnable(catchMeIfYouCan,this);

        players = new ArrayList<>();
    }

    public void startGame(){ game.start();}

    public void stopGame(){
        if (gameState.equals(GameState.LIVE)){
            Location location = catchMeIfYouCan.getConfigManager().getSpawn();
            ArrayList<Player> players1 = new ArrayList<>();
            for (UUID uuid : players){
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(location);
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                player.getEnderChest().clear();
                player.setInvisible(false);
                player.setInvulnerable(false);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.removePotionEffect(PotionEffectType.WEAKNESS);
                players1.add(player);
            }
            players.clear();
            for (Player player : players1){
                new SetScoreboard(player,"lobby",catchMeIfYouCan);
            }
            players1.clear();
            game.getShowHidersRunnable().cancel();
            game.ResetRunnerWinCountdownRunnable();
        }
        sendTitle(" ", " ");
        gameState = GameState.RECRUITING;
        try {
            startCountdownRunnable.cancel();
            startCountdownRunnable = new StartCountdownRunnable(catchMeIfYouCan,this);
        } catch (IllegalStateException exception){}
        game = new Game(catchMeIfYouCan,this);
    }

    // Player management
    public void addPlayer(Player player){
        players.add(player.getUniqueId());

        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.teleport(catchMeIfYouCan.getConfigManager().getLobbySpawn(id));

        if (gameState.equals(GameState.RECRUITING) && players.size() >= catchMeIfYouCan.getConfigManager().getRequiredPlayers()){
            startCountdownRunnable.start();
        }

        new ReloadScoreboard(catchMeIfYouCan,player);
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.getInventory().clear();
        player.getEnderChest().clear();
        player.setInvisible(false);
        player.setInvulnerable(false);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(catchMeIfYouCan.getConfigManager().getSpawn());
        player.sendTitle(" ", " ");

        game.getTeams().remove(player.getUniqueId());

        new ReloadScoreboard(catchMeIfYouCan,player);
        new SetScoreboard(player,"lobby",catchMeIfYouCan);

        if (gameState == GameState.LIVE){
            new WinCheckUtil(this);
        }

        if (gameState == GameState.COUNTDOWN && players.size() < catchMeIfYouCan.getConfigManager().getRequiredPlayers()){
            sendMessage(ChatColor.RED + "There are not enough players, countdown stopped!");
            stopGame();
            return;
        }

        if (gameState == GameState.LIVE && players.size() < catchMeIfYouCan.getConfigManager().getRequiredPlayers()){
            sendMessage(ChatColor.RED + "The game has ended as to many players have left.");
            stopGame();
        }
    }

    public void sendMessage(String message){
        for (UUID uuid : getPlayers()){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title1, String title2){
        for (UUID uuid : getPlayers()){
            Bukkit.getPlayer(uuid).sendTitle(title1,title2);
        }
    }


    // Getters
    public List<UUID> getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getName() {return name;}

    public World getWorld(){ return catchMeIfYouCan.getConfigManager().getHiderSpawn(id).getWorld();}

    // Setters
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
