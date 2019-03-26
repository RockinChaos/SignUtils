package me.RockinChaos.signutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("deprecation")
public class Legacy {
	
	// WELCOME TO THE LAND OF MAKE-BELIEVE! //
    
    public static void updateLegacyInventory(Player player) {
    	player.updateInventory();
    }
    
    public static void setLegacyInHandItem(Player player, ItemStack item) {
    	player.setItemInHand(item);
    }
    
    public static ItemStack getLegacyInHandItem(Player player) {
    	return player.getInventory().getItemInHand();
    }
    
    public static String getLegacySkullOwner(SkullMeta skullMeta) {
    	return skullMeta.getOwner();
    }
    
	public static ItemMeta setLegacySkullOwner(SkullMeta skullMeta, String owner) {
		skullMeta.setOwner(owner);
		return skullMeta;
	}
	
	public static Player getLegacyPlayer(String playerName) {
		return Bukkit.getPlayer(playerName);
	}
}