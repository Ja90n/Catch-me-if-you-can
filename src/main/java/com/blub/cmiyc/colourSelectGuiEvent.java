package com.blub.cmiyc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class colourSelectGuiEvent implements Listener {

    private Cmiyc cmiyc;
    public colourSelectGuiEvent(Cmiyc cmiyc) {
        this.cmiyc = cmiyc;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Catch me if you can - Main menu")){
            if (e.getSlot() == 22){

                //Creating the colour select gui menu
                Inventory colourselectGUI = Bukkit.createInventory(p, 45, ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Colour select menu");

                //Creation of the frame of the colour select gui menu
                ItemStack frame = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
                ItemMeta framemeta = frame.getItemMeta();
                framemeta.setDisplayName("");
                frame.setItemMeta(framemeta);
                for (int i : new int[]{1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44}){
                    colourselectGUI.setItem(i, frame);
                }

                //Creation of the back item
                ItemStack back = new ItemStack(Material.BARRIER);
                ItemMeta backmeta = back.getItemMeta();
                backmeta.setDisplayName(ChatColor.DARK_RED + "Back");
                back.setItemMeta(backmeta);
                colourselectGUI.setItem(0, back);

                //Creation of all the colours that can be selected
                ItemStack white = new ItemStack(Material.WHITE_DYE);
                ItemMeta whitemeta = white.getItemMeta();
                whitemeta.setDisplayName(ChatColor.WHITE + "White");
                white.setItemMeta(whitemeta);
                colourselectGUI.setItem(10, white);

                ItemStack orange = new ItemStack(Material.ORANGE_DYE);
                ItemMeta orangemeta = orange.getItemMeta();
                orangemeta.setDisplayName(ChatColor.GOLD + "Orange");
                orange.setItemMeta(orangemeta);
                colourselectGUI.setItem(11, orange);

                ItemStack magenta = new ItemStack(Material.MAGENTA_DYE);
                ItemMeta magentameta = magenta.getItemMeta();
                magentameta.setDisplayName(ChatColor.DARK_PURPLE + "Magenta");
                magenta.setItemMeta(magentameta);
                colourselectGUI.setItem(12, magenta);

                ItemStack lightblue = new ItemStack(Material.LIGHT_BLUE_DYE);
                ItemMeta lightbluemeta = lightblue.getItemMeta();
                lightbluemeta.setDisplayName(ChatColor.BLUE + "Light Blue");
                lightblue.setItemMeta(lightbluemeta);
                colourselectGUI.setItem(13, lightblue);

                ItemStack yellow = new ItemStack(Material.YELLOW_DYE);
                ItemMeta yellowmeta = yellow.getItemMeta();
                yellowmeta.setDisplayName(ChatColor.YELLOW + "Yellow");
                yellow.setItemMeta(yellowmeta);
                colourselectGUI.setItem(14, yellow);

                ItemStack lime = new ItemStack(Material.LIME_DYE);
                ItemMeta limemeta = lime.getItemMeta();
                limemeta.setDisplayName(ChatColor.GREEN + "Lime");
                lime.setItemMeta(limemeta);
                colourselectGUI.setItem(15, lime);

                ItemStack pink = new ItemStack(Material.PINK_DYE);
                ItemMeta pinkmeta = pink.getItemMeta();
                pinkmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pink");
                pink.setItemMeta(pinkmeta);
                colourselectGUI.setItem(16, pink);

                ItemStack grey = new ItemStack(Material.GRAY_DYE);
                ItemMeta greymeta = grey.getItemMeta();
                greymeta.setDisplayName(ChatColor.GRAY + "Grey");
                grey.setItemMeta(greymeta);
                colourselectGUI.setItem(19, grey);

                ItemStack cyan = new ItemStack(Material.CYAN_DYE);
                ItemMeta cyanmeta = cyan.getItemMeta();
                cyanmeta.setDisplayName(ChatColor.DARK_AQUA + "Cyan");
                cyan.setItemMeta(cyanmeta);
                colourselectGUI.setItem(20, cyan);

                ItemStack purple = new ItemStack(Material.PURPLE_DYE);
                ItemMeta purplemeta = purple.getItemMeta();
                purplemeta.setDisplayName(ChatColor.DARK_PURPLE + "Purple");
                purple.setItemMeta(purplemeta);
                colourselectGUI.setItem(21, purple);

                ItemStack blue = new ItemStack(Material.BLUE_DYE);
                ItemMeta blueemeta = blue.getItemMeta();
                blueemeta.setDisplayName(ChatColor.DARK_BLUE + "Blue");
                blue.setItemMeta(blueemeta);
                colourselectGUI.setItem(22, blue);

                ItemStack brown = new ItemStack(Material.BROWN_DYE);
                ItemMeta brownmeta = brown.getItemMeta();
                brownmeta.setDisplayName(ChatColor.WHITE + "Brown");
                brown.setItemMeta(brownmeta);
                colourselectGUI.setItem(23, brown);

                ItemStack green = new ItemStack(Material.GREEN_DYE);
                ItemMeta greenmeta = green.getItemMeta();
                greenmeta.setDisplayName(ChatColor.DARK_GREEN + "Green");
                green.setItemMeta(greenmeta);
                colourselectGUI.setItem(24, green);

                ItemStack red = new ItemStack(Material.RED_DYE);
                ItemMeta redmeta = red.getItemMeta();
                redmeta.setDisplayName(ChatColor.RED + "Red");
                red.setItemMeta(redmeta);
                colourselectGUI.setItem(25, red);

                ItemStack black = new ItemStack(Material.BLACK_DYE);
                ItemMeta blacketa = black.getItemMeta();
                blacketa.setDisplayName(ChatColor.WHITE + "Black");
                black.setItemMeta(blacketa);
                colourselectGUI.setItem(28, black);

                p.openInventory(colourselectGUI);
            }
        }
        if (e.getView().getTitle().equals(ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC + "Colour select menu")){
            e.setCancelled(true);
            switch (e.getSlot()){
                //When clicked on the back button it goes back to the main menu
                case 0:
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
                    break;
                    //the colour selection
                case 10:
                    p.sendMessage(ChatColor.WHITE + "You have selected white!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"white");
                    break;
                case 11:
                    p.sendMessage(ChatColor.GOLD + "You have selected orange!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"orange");
                    break;
                case 12:
                    p.sendMessage(ChatColor.DARK_PURPLE + "You have selected magenta!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"magenta");
                    break;
                case 13:
                    p.sendMessage(ChatColor.BLUE + "You have selected light blue!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"lblue");
                    break;
                case 14:
                    p.sendMessage(ChatColor.YELLOW + "You have selected yellow!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"yellow");
                    break;
                case 15:
                    p.sendMessage(ChatColor.GREEN + "You have selected lime!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"lime");
                    break;
                case 16:
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "You have selected pink!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"pink");

                    break;
                case 19:
                    p.sendMessage(ChatColor.GRAY + "You have selected grey!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"grey");
                    break;
                case 20:
                    p.sendMessage(ChatColor.DARK_AQUA + "You have selected cyan");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"cyan");
                    break;
                case 21:
                    p.sendMessage(ChatColor.DARK_PURPLE + "You have selected purple!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"purple");
                    break;
                case 22:
                    p.sendMessage(ChatColor.DARK_BLUE + "You have selected blue");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"dblue");
                    break;
                case 23:
                    p.sendMessage(ChatColor.WHITE + "You have selected brown!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"brown");
                    break;
                case 24:
                    p.sendMessage(ChatColor.DARK_GREEN + "You have selected green");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"green");
                    break;
                case 25:
                    p.sendMessage(ChatColor.RED + "You have selected red!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"red");
                    break;
                case 28:
                    p.sendMessage(ChatColor.WHITE + "You have selected black!");
                    cmiyc.colourMap().remove(p.getUniqueId());
                    cmiyc.colourMap().put(p.getUniqueId(),"black");
                    break;
            }
        }
    }
}
