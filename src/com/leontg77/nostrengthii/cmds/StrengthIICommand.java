package com.leontg77.nostrengthii.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

import com.leontg77.nostrengthii.Main;
import com.leontg77.nostrengthii.listeners.BrewListener;
import com.leontg77.nostrengthii.listeners.DrinkListener;

/**
 * BParanoia command.
 * <p> 
 * Command used to enable or disable the scenario.
 * 
 * @author LeonTG77
 */
public class StrengthIICommand implements CommandExecutor {
	private static final String PREFIX = "§7[§cNo Strength II§7] §f";
	private static final String PERMISSION = "bparanoia.manage";
	
	public BrewListener brew = new BrewListener();
	public DrinkListener move = new DrinkListener();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check if the have permission, if not send them a message and return.
		if (!sender.hasPermission(PERMISSION)) {
			sender.sendMessage(PREFIX + ChatColor.RED + "You don't have access to this.");
			return true;
		}
		
		// check if they typed anything else than the command itself, if not send usage and return.
		if (args.length == 0) {
			sender.sendMessage(PREFIX + "Usage: /strengthii <enable|disable>");
			return true;
		}
		
		// check if they typed /egg enable, if so do the command.
		if (args[0].equalsIgnoreCase("enable")) {
			// check if the scenario is enabled, if not tell them so and return.
			if (Main.isOn()) {
				sender.sendMessage(PREFIX + "StengthII is already disabled.");
				return true;
			}
			
			// send them a message and set enabled to be true
			sender.sendMessage(PREFIX + "Strength II is now enabled.");
			Main.setOn(true);
			
			// unregister the eventhandles for the scenario.
			HandlerList.unregisterAll(brew);
			HandlerList.unregisterAll(move);
			return true;
		}

		// check if they typed /egg enable, if so do the command.
		if (args[0].equalsIgnoreCase("disable")) {
			// check if the scenario wasn't enabled, if not tell them so and return.
			if (!Main.isOn()) {
				sender.sendMessage(PREFIX + "Strength II is not enabled.");
				return true;
			}

			// send them a message and set enabled to be false
			sender.sendMessage(PREFIX + "Strength II has been disabled.");
			Main.setOn(false);
			
			// register the eventhandles for the scenario that disables strength.
			PluginManager manager = Bukkit.getPluginManager();
			manager.registerEvents(move, Main.plugin);
			manager.registerEvents(brew, Main.plugin);
			return true;
		}
		
		// they didn't type enable or disable, send usage.
		sender.sendMessage(PREFIX + "Usage: /strengthii <enable|disable>");
		return true;
	}
}