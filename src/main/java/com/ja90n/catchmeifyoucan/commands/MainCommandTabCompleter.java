package com.ja90n.catchmeifyoucan.commands;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTabCompleter implements TabCompleter {

    private CatchMeIfYouCan catchMeIfYouCan;

    public MainCommandTabCompleter(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                list.clear();
                list.add("join");
                list.add("leave");
                list.add("help");
                list.add("list");
                list.add("start");
                list.add("stop");
                break;
            case 2:
                switch (args[0]) {
                    case "join":
                        list.clear();
                        for (Arena arena : catchMeIfYouCan.getArenaManager().getArenas()) {
                            list.add(arena.getName());
                        }
                        break;
                }
        }
        return list;
    }
}

