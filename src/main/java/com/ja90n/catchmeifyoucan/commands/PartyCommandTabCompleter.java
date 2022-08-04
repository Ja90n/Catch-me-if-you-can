package com.ja90n.catchmeifyoucan.commands;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import com.ja90n.catchmeifyoucan.instances.Party;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyCommandTabCompleter implements TabCompleter {

    private CatchMeIfYouCan catchMeIfYouCan;

    public PartyCommandTabCompleter(CatchMeIfYouCan catchMeIfYouCan){
        this.catchMeIfYouCan = catchMeIfYouCan;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> list = new ArrayList<>();

        if (sender instanceof Player){
            Player player = (Player) sender;
            switch (args.length){
                case 1:
                    list.add("join");
                    list.add("leave");
                    list.add("create");
                    list.add("invite");
                    list.add("list");
                    list.add("help");
                    list.add("promote");
                    list.add("disband");
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("join")){
                        for (UUID uuid : catchMeIfYouCan.getPartyManager().getPlayers()){
                            if (!uuid.equals(player.getUniqueId())){
                                list.add(Bukkit.getPlayer(uuid).getDisplayName());
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("invite")){
                        for (Player target : Bukkit.getOnlinePlayers()){
                            if (!target.equals(player)){
                                list.add(target.getDisplayName());
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("promote")){
                        if (catchMeIfYouCan.getPartyManager().getParty(player) != null){
                            for (UUID uuid : catchMeIfYouCan.getPartyManager().getParty(player).getPlayers()){
                                if (!uuid.equals(player.getUniqueId())){
                                    list.add(Bukkit.getPlayer(uuid).getDisplayName());
                                }
                            }
                        }
                    }
                    break;
            }
        }

        return list;
    }
}
