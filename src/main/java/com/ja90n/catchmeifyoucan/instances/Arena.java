package com.ja90n.catchmeifyoucan.instances;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.GameState;
import com.ja90n.catchmeifyoucan.runnables.RunnerWinCountdownRunnable;
import com.ja90n.catchmeifyoucan.runnables.StartCountdownRunnable;
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
    private RunnerWinCountdownRunnable runnerWinCountdownRunnable;

    public Arena(CatchMeIfYouCan catchMeIfYouCan, int id, String name) {
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.id = id;
        this.name = name;
        this.game = new Game(catchMeIfYouCan,this);
        this.gameState = GameState.RECRUITING;
        this.startCountdownRunnable = new StartCountdownRunnable(catchMeIfYouCan,this);
        this.runnerWinCountdownRunnable = new RunnerWinCountdownRunnable(catchMeIfYouCan,this);

        players = new ArrayList<>();
    }

    public void startGame(){ game.start();}

    public void stopGame(){
        if (gameState.equals(GameState.LIVE)){
            Location location = catchMeIfYouCan.getConfigManager().getLobbySpawn(id);
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
            }
            game.getShowHidersRunnable().cancel();
            runnerWinCountdownRunnable.cancel();
            players.clear();
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
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.teleport(catchMeIfYouCan.getConfigManager().getLobbySpawn(id));
        player.sendTitle("", "");

        game.getTeams().remove(player.getUniqueId());

        if (gameState == GameState.LIVE){
            if (game.getHiders().contains(player.getUniqueId())){
                int hinderAmount = 0;
                for (UUID uuid : getGame().getTeams().keySet()){
                    if (getGame().getTeams().get(uuid).equals("hider")){
                        hinderAmount++;
                    }
                }
                if (hinderAmount <= 0) {
                    sendTitle(ChatColor.WHITE.toString() + ChatColor.BOLD + player.getDisplayName() + ChatColor.LIGHT_PURPLE + " has won the game!", ChatColor.GRAY + "Thank you for playing!");
                    stopGame();
                    return;
                }
            } else if (game.getSeekers().contains(player.getUniqueId())){
                if (game.getSeekers().size() == 0){
                    sendMessage(ChatColor.RED + "All seekers have left so the game is stopped!");
                    stopGame();
                    return;
                }
            }
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

    public RunnerWinCountdownRunnable getRunnerWinCountdownRunnable() {
        return runnerWinCountdownRunnable;
    }

    // Setters
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
