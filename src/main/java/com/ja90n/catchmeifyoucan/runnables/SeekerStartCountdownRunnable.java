package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SeekerStartCountdownRunnable extends BukkitRunnable {

    private CatchMeIfYouCan catchMeIfYouCan;
    private Player player;
    private Arena arena;
    private int countdownSeconds;

    public SeekerStartCountdownRunnable(CatchMeIfYouCan catchMeIfYouCan, Player player, Arena arena){
        this.player = player;
        this.arena = arena;
        this.catchMeIfYouCan = catchMeIfYouCan;
        this.countdownSeconds = catchMeIfYouCan.getConfigManager().getSeekerWaitTime();
        player.teleport(catchMeIfYouCan.getConfigManager().getSeekerSpawn(arena.getId()));
        player.sendMessage(ChatColor.LIGHT_PURPLE + "You are a Catcher!");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Game has been started! You have to wait " + catchMeIfYouCan.getConfigManager().getSeekerWaitTime() + " seconds to be released!");
        player.sendTitle(ChatColor.LIGHT_PURPLE + "Game has been started!", ChatColor.GRAY + "You have to wait " + catchMeIfYouCan.getConfigManager().getSeekerWaitTime() + " seconds to be released!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,20 * catchMeIfYouCan.getConfigManager().getSeekerWaitTime(),10,false,false));
        arena.getGame().toggleRunnableCheck(player,true);
        runTaskTimer(catchMeIfYouCan,2,20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0){
            player.sendTitle(ChatColor.BOLD.toString() + ChatColor.BLUE + "You got released!" , ChatColor.GRAY + "Good luck with catching the runners!");
            player.teleport(catchMeIfYouCan.getConfigManager().getSeekerSpawn(arena.getId()));
            arena.sendMessage(ChatColor.RED + "The Catcher has been released!");
            arena.getGame().toggleRunnableCheck(player,false);
            arena.getGame().getShowHidersRunnable().start();
            cancel();
        }
        player.sendTitle( ChatColor.BLUE + "You will be released in " + ChatColor.WHITE + countdownSeconds + ChatColor.BLUE + " second" + (countdownSeconds == 1 ? "" : "s") + "."," ");
        if (countdownSeconds <= 10 || countdownSeconds % 15 == 0){
            arena.sendMessage(ChatColor.RED + "The Catcher will be released in " + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".");
        }
        countdownSeconds--;
    }
}