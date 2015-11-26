package com.leontg77.nostrengthii.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Brew listener
 * <p> 
 * Class used to listen for the brew event and cancel it if they're making strength 2.
 * 
 * @author LeonTG77
 */
public class BrewListener implements Listener {

	@EventHandler
    public void onBrew(BrewEvent event) {
    	BrewerInventory inv = event.getContents();
    	
    	// if the inventory doesn't contain glowstone AND a strength potion, return.
    	if (!containsStrengthOrGlowstone(inv)) {
    		return;
        }
    	
    	// cancel. 
    	event.setCancelled(true);

		Block block = event.getBlock();
		Location blockL = block.getLocation();
		
		// loop thru everyone online.
		for (Player online : Bukkit.getOnlinePlayers()) {
			Location playerL = online.getLocation();
			
			// if they're within a radius send them a message.
			if (block.getWorld() == online.getWorld() && playerL.distance(blockL) < 15) {
				online.sendMessage(ChatColor.RED + "Strength II is disabled, brewing cancelled.");
			}
		}
    }
    
	/**
	 * Check if the given inventory contains the ingrediens for strength 2.
	 * 
	 * @param inv The inventory checking.
	 * @return True if it does, false otherwise.
	 */
    public boolean containsStrengthOrGlowstone(BrewerInventory inv) {
    	boolean glowstone = false;
    	boolean potion = false;
    	
    	// loop thru all items in the inventory.
    	for (ItemStack item : inv.getContents()) {
    		// if the item is null, hop over it.
    		if (item == null) {
    			continue;
    		}
    		
    		// the item is a glowstone, set that to true.
    		if (item.getType() == Material.GLOWSTONE_DUST) {
    			glowstone = true;
    		}
    		
    		// from now we don't want to care if its not a potion.
    		if (item.getType() != Material.POTION) {
    			continue;
    		}
    		
    		// if the potion has the durability of a strength potion, set potion to true.
			if (item.getDurability() == 8265 || item.getDurability() == 8201) {
				potion = true;
			}
    	}
    	
    	// return true if it had glowstone AND a strenght potion
    	return glowstone && potion;
    }
}