package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ShowHidersRunnable extends BukkitRunnable {

    private CatchMeIfYouCan catchMeIfYouCan;
    private Arena arena;

    public ShowHidersRunnable(CatchMeIfYouCan catchMeIfYouCan, Arena arena){
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.arena = arena;
    }

    public void start(){
        runTaskTimer(catchMeIfYouCan,600,600);
    }

    @Override
    public void run() {
        for (UUID uuid : arena.getGame().getHiders()){
            Bukkit.getPlayer(uuid).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,100,0,false,false));
            Bukkit.getPlayer(uuid).addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100,0,false,false));
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + "You are now glowing for the next 5 seconds");
        }
        for (UUID uuid : arena.getGame().getSeekers()){
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.BLUE + "All runners are now glowing for 5 seconds");
        }
    }
}