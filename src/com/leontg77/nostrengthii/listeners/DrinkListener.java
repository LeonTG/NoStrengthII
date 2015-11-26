package com.leontg77.nostrengthii.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Drink listener.
 * <p>
 * Class used to check for the consume event to cancel drinking of strength 2.
 * 
 * @author LeonTG77
 */
public class DrinkListener implements Listener {

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		
		// if the item is not a potion we don't want to care.
		if (item == null || item.getType() != Material.POTION) {
			return;
		}
		
		// if the potion has the durability of a strength 2 potion, tell them and cancel.
		if (item.getDurability() == 8233) {
			player.sendMessage(ChatColor.RED + "Strength II is disabled.");
			event.setCancelled(true);
		}
	}
}