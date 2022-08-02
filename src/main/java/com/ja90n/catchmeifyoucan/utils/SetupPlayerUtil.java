package com.ja90n.catchmeifyoucan.utils;

import com.ja90n.catchmeifyoucan.CatchMeIfYouCan;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.concurrent.ThreadLocalRandom;

public class SetupPlayerUtil {
    public SetupPlayerUtil(Player player, String team, CatchMeIfYouCan catchMeIfYouCan){
        player.closeInventory();
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);

        if (team.equals("seeker")){
            // Set the seeker inventory
            player.getInventory().clear();
            player.getInventory().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
            player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
            player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));

            // Gamemode and potions
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999 ,1, false, false));
        } else {
            // Setting the hider inventory
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headmeta = (SkullMeta) head.getItemMeta();
            headmeta.setOwningPlayer(player);
            head.setItemMeta(headmeta);

            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) new ItemStack(Material.LEATHER_BOOTS).getItemMeta();

            switch (ThreadLocalRandom.current().nextInt(0,16)){
                case 0:
                    leatherArmorMeta.setColor(Color.BLACK);
                    break;
                case 1:
                    leatherArmorMeta.setColor(Color.FUCHSIA);
                    break;
                case 2:
                    leatherArmorMeta.setColor(Color.RED);
                    break;
                case 3:
                    leatherArmorMeta.setColor(Color.BLUE);
                    break;
                case 4:
                    leatherArmorMeta.setColor(Color.GREEN);
                    break;
                case 5:
                    leatherArmorMeta.setColor(Color.TEAL);
                    break;
                case 6:
                    leatherArmorMeta.setColor(Color.PURPLE);
                    break;
                case 7:
                    leatherArmorMeta.setColor(Color.GRAY);
                    break;
                case 8:
                    leatherArmorMeta.setColor(Color.YELLOW);
                    break;
                case 9:
                    leatherArmorMeta.setColor(Color.ORANGE);
                    break;
                case 10:
                    leatherArmorMeta.setColor(Color.MAROON);
                    break;
                case 11:
                    leatherArmorMeta.setColor(Color.SILVER);
                    break;
                case 12:
                    leatherArmorMeta.setColor(Color.NAVY);
                    break;
                case 13:
                    leatherArmorMeta.setColor(Color.WHITE);
                    break;
                case 14:
                    leatherArmorMeta.setColor(Color.AQUA);
                    break;
                case 15:
                    leatherArmorMeta.setColor(Color.OLIVE);
                    break;
            }

            player.getInventory().setHelmet(head);
            player.getInventory().setChestplate(new ItemStack (Material.LEATHER_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack (Material.LEATHER_LEGGINGS));
            player.getInventory().setBoots(new ItemStack (Material.LEATHER_BOOTS));

            player.getInventory().getChestplate().setItemMeta(leatherArmorMeta);
            player.getInventory().getLeggings().setItemMeta(leatherArmorMeta);
            player.getInventory().getBoots().setItemMeta(leatherArmorMeta);

            //Potion
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*catchMeIfYouCan.getConfigManager().getSeekerWaitTime(),2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,999999999,1,false,false));
        }
    }
}
