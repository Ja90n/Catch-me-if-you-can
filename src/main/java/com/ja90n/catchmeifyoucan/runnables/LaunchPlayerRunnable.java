package com.ja90n.catchmeifyoucan.runnables;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class LaunchPlayerRunnable extends BukkitRunnable {

    private Vector vector;
    private Entity entity;

    public LaunchPlayerRunnable(CatchMeIfYouCan catchMeIfYouCan, Vector vector, Entity entity){
        this.vector = vector;
        this.entity = entity;
        runTaskLater(catchMeIfYouCan,5);
    }


    @Override
    public void run() {
        entity.setVelocity(vector);
    }
}
