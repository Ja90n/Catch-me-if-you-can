package com.blub.cmiyc;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class gamemenuGuiEvent implements Listener {

    private Cmiyc cmiyc;
    public gamemenuGuiEvent(Cmiyc cmiyc) {
        this.cmiyc = cmiyc;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Catch me if you can - Main menu")){
            if (e.getSlot() == 24){
                //Creating the main gui
                Inventory gamemenu = Bukkit.createInventory(p, 45, ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Game menu");

                //Creation of the frame of the main gui menu
                ItemStack frame = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
                ItemMeta framemeta = frame.getItemMeta();
                frame.setItemMeta(framemeta);
                for (int i : new int[]{1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}){
                    gamemenu.setItem(i, frame);
                }

                //Creation of the back item
                ItemStack back = new ItemStack(Material.BARRIER);
                ItemMeta backmeta = back.getItemMeta();
                backmeta.setDisplayName(ChatColor.DARK_RED + "Back");
                back.setItemMeta(backmeta);
                gamemenu.setItem(0, back);

                //Creation of start and stop buttons
                if (cmiyc.issGameActive()){
                    //Creation of the frame of the main gui menu
                    ItemStack stop = new ItemStack(Material.RED_CONCRETE);
                    ItemMeta stopmeta = stop.getItemMeta();
                    stopmeta.setDisplayName(ChatColor.RED + "Stop the game");
                    stop.setItemMeta(stopmeta);
                    gamemenu.setItem(20,stop);
                } else {
                    ItemStack start = new ItemStack(Material.GREEN_CONCRETE);
                    ItemMeta startmeta = start.getItemMeta();
                    startmeta.setDisplayName(ChatColor.GREEN + "Start the game");
                    start.setItemMeta(startmeta);
                    gamemenu.setItem(20,start);
                }

                //Creation of list player button
                ItemStack list = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta listmeta = list.getItemMeta();
                listmeta.setDisplayName(ChatColor.BLUE + "List all the players");
                list.setItemMeta(listmeta);
                gamemenu.setItem(22,list);

                //Comming soon
                ItemStack soon = new ItemStack(Material.BLACK_CONCRETE);
                ItemMeta soonmeta = soon.getItemMeta();
                soonmeta.setDisplayName(ChatColor.WHITE + "Comming soon!");
                soon.setItemMeta(soonmeta);
                gamemenu.setItem(24,soon);

                p.openInventory(gamemenu);
            }
        } if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Game menu")){
            e.setCancelled(true);
            if (e.getSlot() == 20){
                if (cmiyc.issGameActive()){

                    //Stopping the game
                    for (Player target : Bukkit.getOnlinePlayers()){
                        if (cmiyc.getHiderplayers().contains(target.getUniqueId())){
                            cmiyc.getHiderplayers().remove(target.getUniqueId());
                            target.getInventory().clear();
                        } else if (cmiyc.getSeekerplayers().contains(target.getUniqueId())){
                            cmiyc.getSeekerplayers().remove(target.getUniqueId());
                            target.getInventory().clear();
                        } else if (cmiyc.getSpectatorplayers().contains(target.getUniqueId())) {
                            cmiyc.getSpectatorplayers().remove(target.getUniqueId());
                            target.getInventory().clear();
                        }

                        //Removing effects
                        for (Player target2 : Bukkit.getOnlinePlayers()){
                            target.showPlayer(target2);
                            target.removePotionEffect(PotionEffectType.INVISIBILITY);
                            target.removePotionEffect(PotionEffectType.SPEED);
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            target.getInventory().clear();
                        }

                        //Teleporting player
                        double hx = cmiyc.getConfig().getDouble("lobby.x");
                        double hy = cmiyc.getConfig().getDouble("lobby.y");
                        double hz = cmiyc.getConfig().getDouble("lobby.z");
                        Location lobby = new Location(Bukkit.getWorld(cmiyc.getConfig().getString("world")), hx, hy, hz, 0, 0);
                        target.teleport(lobby);

                        //Sending message
                        target.sendMessage(ChatColor.BLUE + "Game has been stoped!");
                    }
                    p.closeInventory();
                    cmiyc.isgameactive = false;
                } else {
                    //Starting the game
                    if (!(cmiyc.getLobbyplayers().size() > 2)){
                        e.getWhoClicked().sendMessage(ChatColor.RED + "There are not enough players to begin the game!");
                    } else {

                        int ran = ThreadLocalRandom.current().nextInt(0, cmiyc.getLobbyplayers().size());
                        cmiyc.getSeekerplayers().add(cmiyc.getLobbyplayers().get(ran));
                        //Setting the players in there teams
                        for (Player target : Bukkit.getOnlinePlayers()){
                            if (!cmiyc.getSeekerplayers().contains(target.getUniqueId())){
                                cmiyc.getHiderplayers().add(target.getUniqueId());
                            }
                            cmiyc.getLobbyplayers().remove(target.getUniqueId());
                        }

                        for (Player target : Bukkit.getOnlinePlayers()) {
                            if (cmiyc.getSeekerplayers().contains(target.getUniqueId())) {
                                //Setting up the seeker
                                Player seeker = Bukkit.getPlayer(target.getUniqueId());

                                //Set the seeker inventory
                                seeker.getInventory().clear();
                                seeker.getInventory().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
                                seeker.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                                seeker.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                seeker.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                seeker.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));

                                //Teleporting the player
                                double hx = cmiyc.getConfig().getDouble("seekerspawn.x");
                                double hy = cmiyc.getConfig().getDouble("seekerspawn.y");
                                double hz = cmiyc.getConfig().getDouble("seekerspawn.z");
                                Location seekerspawn = new Location(Bukkit.getWorld(cmiyc.getConfig().getString("world")), hx, hy, hz, 0, 0);
                                seeker.teleport(seekerspawn);

                                //Set the gamemode and potions of player
                                seeker.setGameMode(GameMode.ADVENTURE);
                                seeker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 900 ,10, false, true));
                                seeker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 900 ,100, false, true));
                                seeker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999 ,1, false, false));

                                //Send message
                                seeker.sendMessage(ChatColor.BLUE + "You have to wait 45 to start!");

                            } else {
                                //Setting up the hider
                                Player hider = Bukkit.getPlayer(target.getUniqueId());

                                //Creation of the player skull
                                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta headmeta = (SkullMeta) head.getItemMeta();
                                headmeta.setOwningPlayer(hider);
                                head.setItemMeta(headmeta);
                                hider.getInventory().setHelmet(head);

                                //Creating player armor
                                hider.getInventory().setChestplate(new ItemStack (Material.LEATHER_CHESTPLATE));
                                hider.getInventory().setLeggings(new ItemStack (Material.LEATHER_LEGGINGS));
                                hider.getInventory().setBoots(new ItemStack (Material.LEATHER_BOOTS));

                                //Setting the colour of the hider armor
                                LeatherArmorMeta armorMeta = (LeatherArmorMeta) hider.getInventory().getChestplate().getItemMeta();
                                try {
                                    switch (cmiyc.colourMap.get(hider.getUniqueId())) {
                                        //When clicked on the back button it goes back to the main menu
                                        case "white":
                                            armorMeta.setColor(Color.WHITE);
                                            break;
                                        case "orange":
                                            armorMeta.setColor(Color.ORANGE);
                                            break;
                                        case "magenta":
                                            armorMeta.setColor(Color.fromRGB(223, 125, 224));
                                            break;
                                        case "lblue":
                                            armorMeta.setColor(Color.TEAL);
                                            break;
                                        case "yellow":
                                            armorMeta.setColor(Color.YELLOW);
                                            break;
                                        case "lime":
                                            armorMeta.setColor(Color.LIME);
                                            break;
                                        case "pink":
                                            armorMeta.setColor(Color.fromRGB(255, 65, 173));
                                            break;
                                        case "grey":
                                            armorMeta.setColor(Color.GRAY);
                                            break;
                                        case "cyan":
                                            armorMeta.setColor(Color.fromRGB(0, 195, 255));
                                            break;
                                        case "purple":
                                            armorMeta.setColor(Color.PURPLE);
                                            break;
                                        case "dblue":
                                            armorMeta.setColor(Color.BLUE);
                                            break;
                                        case "brown":
                                            armorMeta.setColor(Color.fromRGB(185, 122, 86));
                                            break;
                                        case "green":
                                            armorMeta.setColor(Color.GREEN);
                                            break;
                                        case "red":
                                            armorMeta.setColor(Color.RED);
                                            break;
                                        case "black":
                                            armorMeta.setColor(Color.BLACK);
                                            break;
                                    }
                                } catch (NullPointerException ex){
                                    armorMeta.setColor(Color.WHITE);
                                }

                                hider.getInventory().getChestplate().setItemMeta(armorMeta);
                                hider.getInventory().getLeggings().setItemMeta(armorMeta);
                                hider.getInventory().getBoots().setItemMeta(armorMeta);

                                //Set the gamemode player
                                hider.setGameMode(GameMode.ADVENTURE);
                                hider.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999 , 0, false, false));

                                //Teleport player
                                double hx = cmiyc.getConfig().getDouble("hiderspawn.x");
                                double hy = cmiyc.getConfig().getDouble("hiderspawn.y");
                                double hz = cmiyc.getConfig().getDouble("hiderspawn.z");
                                Location hiderspawn = new Location(Bukkit.getWorld(cmiyc.getConfig().getString("world")), hx, hy, hz, 0, 0);
                                hider.teleport(hiderspawn);

                                //Send message
                                hider.sendMessage(ChatColor.BLUE + "You have 45 to run away, good luck!");
                            }
                        }
                        p.closeInventory();
                        cmiyc.isgameactive = true;
                    }
                }

            } else if (e.getSlot() == 0){
                p.closeInventory();
                //Creating the main gui
                Inventory maingui = Bukkit.createInventory(p, 45, ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Catch me if you can - Main menu");

                //Creation of the frame of the main gui menu
                ItemStack frame = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
                ItemMeta framemeta = frame.getItemMeta();
                framemeta.setDisplayName("");
                frame.setItemMeta(framemeta);
                for (int i : new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}){
                    maingui.setItem(i, frame);
                }

                if (cmiyc.getLobbyplayers().contains(p.getUniqueId())){
                    //Creating leave the gamemode item for maingui
                    ItemStack leave = new ItemStack(Material.WOODEN_SWORD);
                    ItemMeta leavemeta = leave.getItemMeta();
                    leavemeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Leave");
                    leave.setItemMeta(leavemeta);
                    maingui.setItem(20, leave);
                } else {
                    //Creating join gamemode item for maingui
                    ItemStack join = new ItemStack(Material.STONE_SWORD);
                    ItemMeta joinmeta = join.getItemMeta();
                    joinmeta.setDisplayName(ChatColor.BLUE.toString() + ChatColor.BOLD + "Join");
                    join.setItemMeta(joinmeta);
                    maingui.setItem(20, join);
                }

                //Creating colour item selector for maingui
                ItemStack colour = new ItemStack(Material.PURPLE_DYE);
                ItemMeta colourmeta = colour.getItemMeta();
                colourmeta.setDisplayName(ChatColor.GREEN + "Colour selector");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Select a colour for your hider outfit!");
                colourmeta.setLore(lore);
                colour.setItemMeta(colourmeta);
                maingui.setItem(22, colour);

                //Creating game menu item selector for maingui
                ItemStack dev = new ItemStack(Material.DEBUG_STICK);
                ItemMeta devmeta = dev.getItemMeta();
                devmeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Game menu");
                dev.setItemMeta(devmeta);
                maingui.setItem(24, dev);

                //Showing selected colour
                try {
                    switch (cmiyc.colourMap.get(p.getUniqueId())) {
                        //When clicked on the back button it goes back to the main menu
                        case "white":
                            ItemStack white = new ItemStack(Material.WHITE_DYE);
                            ItemMeta whitemeta = white.getItemMeta();
                            whitemeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.WHITE + "White");
                            white.setItemMeta(whitemeta);
                            maingui.setItem(8, white);
                            break;
                        case "orange":
                            ItemStack orange = new ItemStack(Material.ORANGE_DYE);
                            ItemMeta orangemeta = orange.getItemMeta();
                            orangemeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.GOLD + "Orange");
                            orange.setItemMeta(orangemeta);
                            maingui.setItem(8, orange);
                            break;
                        case "magenta":
                            ItemStack magenta = new ItemStack(Material.MAGENTA_DYE);
                            ItemMeta magentameta = magenta.getItemMeta();
                            magentameta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.DARK_PURPLE + "Magenta");
                            magenta.setItemMeta(magentameta);
                            maingui.setItem(8, magenta);
                            break;
                        case "lblue":
                            ItemStack lightblue = new ItemStack(Material.LIGHT_BLUE_DYE);
                            ItemMeta lightbluemeta = lightblue.getItemMeta();
                            lightbluemeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.BLUE + "Light Blue");
                            lightblue.setItemMeta(lightbluemeta);
                            maingui.setItem(8, lightblue);
                            break;
                        case "yellow":
                            ItemStack yellow = new ItemStack(Material.YELLOW_DYE);
                            ItemMeta yellowmeta = yellow.getItemMeta();
                            yellowmeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.YELLOW + "Yellow");
                            yellow.setItemMeta(yellowmeta);
                            maingui.setItem(8, yellow);
                            break;
                        case "lime":
                            ItemStack lime = new ItemStack(Material.LIME_DYE);
                            ItemMeta limemeta = lime.getItemMeta();
                            limemeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.GREEN + "Lime");
                            lime.setItemMeta(limemeta);
                            maingui.setItem(8, lime);
                            break;
                        case "pink":
                            ItemStack pink = new ItemStack(Material.PINK_DYE);
                            ItemMeta pinkmeta = pink.getItemMeta();
                            pinkmeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.LIGHT_PURPLE + "Pink");
                            pink.setItemMeta(pinkmeta);
                            maingui.setItem(8, pink);
                            break;
                        case "grey":
                            ItemStack grey = new ItemStack(Material.GRAY_DYE);
                            ItemMeta greymeta = grey.getItemMeta();
                            greymeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.GRAY + "Grey");
                            grey.setItemMeta(greymeta);
                            maingui.setItem(8, grey);
                            break;
                        case "cyan":
                            ItemStack cyan = new ItemStack(Material.CYAN_DYE);
                            ItemMeta cyanmeta = cyan.getItemMeta();
                            cyanmeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.DARK_AQUA + "Cyan");
                            cyan.setItemMeta(cyanmeta);
                            maingui.setItem(8, cyan);
                            break;
                        case "purple":
                            ItemStack purple = new ItemStack(Material.PURPLE_DYE);
                            ItemMeta purplemeta = purple.getItemMeta();
                            purplemeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.DARK_PURPLE + "Purple");
                            purple.setItemMeta(purplemeta);
                            maingui.setItem(8, purple);
                            break;
                        case "dblue":
                            ItemStack blue = new ItemStack(Material.BLUE_DYE);
                            ItemMeta blueemeta = blue.getItemMeta();
                            blueemeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.DARK_BLUE + "Blue");
                            blue.setItemMeta(blueemeta);
                            maingui.setItem(8, blue);
                            break;
                        case "brown":
                            ItemStack brown = new ItemStack(Material.BROWN_DYE);
                            ItemMeta brownmeta = brown.getItemMeta();
                            brownmeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.WHITE + "Brown");
                            brown.setItemMeta(brownmeta);
                            maingui.setItem(8, brown);
                            break;
                        case "green":
                            ItemStack green = new ItemStack(Material.GREEN_DYE);
                            ItemMeta greenmeta = green.getItemMeta();
                            greenmeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.DARK_GREEN + "Green");
                            green.setItemMeta(greenmeta);
                            maingui.setItem(8, green);
                            break;
                        case "red":
                            ItemStack red = new ItemStack(Material.RED_DYE);
                            ItemMeta redmeta = red.getItemMeta();
                            redmeta.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.RED + "Red");
                            red.setItemMeta(redmeta);
                            maingui.setItem(8, red);
                            break;
                        case "black":
                            ItemStack black = new ItemStack(Material.BLACK_DYE);
                            ItemMeta blacketa = black.getItemMeta();
                            blacketa.setDisplayName(ChatColor.WHITE + "You have selected colour " + ChatColor.WHITE + "Black");
                            black.setItemMeta(blacketa);
                            maingui.setItem(8, black);
                            break;
                    }
                } catch (NullPointerException ex) {
                    ItemStack empty = new ItemStack(Material.WHITE_DYE);
                    ItemMeta emptymeta = empty.getItemMeta();
                    emptymeta.setDisplayName(ChatColor.WHITE + "You have not selected a colour!");
                    empty.setItemMeta(emptymeta);
                    maingui.setItem(8, empty);
                }

                p.openInventory(maingui);
            }
        }
    }
}
