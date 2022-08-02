package com.ja90n.catchmeifyoucan.events;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.utils.SetScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PlayerJoin(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        new SetScoreboard(event.getPlayer(), "lobby",catchMeIfYouCan);
    }
}
