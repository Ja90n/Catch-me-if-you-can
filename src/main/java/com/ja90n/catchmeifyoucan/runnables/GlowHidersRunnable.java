package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GlowHidersRunnable extends BukkitRunnable {

    private CatchMeIfYouCan catchMeIfYouCan;
    private Arena arena;
    private int timeRun;

    public GlowHidersRunnable(CatchMeIfYouCan catchMeIfYouCan, Arena arena){
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.arena = arena;
        timeRun = catchMeIfYouCan.getConfigManager().getGlowHiderTime();
    }

    public void start(){
        runTaskTimer(catchMeIfYouCan,2,20);
    }

    @Override
    public void run() {
        for (UUID uuid : arena.getPlayers()){
            Player player = Bukkit.getPlayer(uuid);
            if (timeRun >= 60){
                int minutes = timeRun/60;
                int seconds = timeRun - (60*minutes);
                if (seconds <= 9){
                    player.getScoreboard().getTeam("TimeToGlow").setSuffix(ChatColor.WHITE.toString() + minutes + ":0" + seconds);
                } else {
                    player.getScoreboard().getTeam("TimeToGlow").setSuffix(ChatColor.WHITE.toString() + minutes + ":" + seconds);
                }
            } else {
                if (timeRun <= 9){
                    player.getScoreboard().getTeam("TimeToGlow").setSuffix(ChatColor.WHITE.toString() + "00:0" + timeRun);
                } else {
                    player.getScoreboard().getTeam("TimeToGlow").setSuffix(ChatColor.WHITE.toString() + "00:" + timeRun);
                }
            }
        }
        if (timeRun==0){
            for (UUID uuid : arena.getGame().getHiders()){
                Bukkit.getPlayer(uuid).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,60,0,false,false));
                Bukkit.getPlayer(uuid).addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100,0,false,false));
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_RED + "You are now visible for 3 seconds");
            }
            for (UUID uuid : arena.getGame().getSeekers()){
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.DARK_RED + "The runners are now visible for 3 seconds");
            }
            timeRun = catchMeIfYouCan.getConfigManager().getGlowHiderTime() + 1;
        }
        timeRun--;
    }
}