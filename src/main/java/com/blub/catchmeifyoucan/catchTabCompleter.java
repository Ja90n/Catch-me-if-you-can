package com.blub.catchmeifyoucan;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class catchTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List <String> result = new ArrayList<>();
        if (args.length == 1){
            result.add("join");
            result.add("leave");
            result.add("start");
            result.add("stop");
            result.add("list");
        } else if (args[0].equalsIgnoreCase("join") && args.length==2){
            result.add("seeker");
            result.add("hider");
            result.add("spectator");
        }
        return result;
    }
}
