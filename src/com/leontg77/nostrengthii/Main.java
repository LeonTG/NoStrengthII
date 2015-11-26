package com.leontg77.nostrengthii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.leontg77.nostrengthii.cmds.StrengthIICommand;

/**
 * Main class of the plugin.
 * 
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
	private static boolean enabled;
	public static Main plugin;

	@Override
	public void onDisable() {
		// print a message to the console saying it has been disabled.
		PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " has been disabled.");
		
		// set the plugin field to null.
		plugin = null;
	}
	
	@Override
	public void onEnable() {
		// print a message to the console saying it has been enabled.
		PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " v" + file.getVersion() + " has been enabled.");
		getLogger().info("Plugin is made by LeonTG77.");
		
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		getConfig().addDefault("enabled", true);
		saveConfig(); 
		
		enabled = getConfig().getBoolean("enabled", true);
		
		StrengthIICommand command = new StrengthIICommand();
		
		// register the /strengthii commands.
		getCommand("strengthii").setExecutor(command);
		
		// if strength 2 is disabled, register the blockers.
		if (!enabled) {
			PluginManager manager = Bukkit.getPluginManager();
			manager.registerEvents(command.move, this);
			manager.registerEvents(command.brew, this);
		}
		
		// set the plugin field to this class.
		plugin = this;
	}
	
	/**
	 * Enable or disable strength 2.
	 * 
	 * @param enable True to have strength 2 on, false to have it off.
	 */
	public static void setOn(boolean enable) {
		// set the config and enabled variable to the boolean given.
		plugin.getConfig().set("enabled", enable);
		plugin.saveConfig();
		
		enabled = enable;
	}
	
	/**
	 * Check if the strength 2 is enabled.
	 * 
	 * @return True if it is, false otherwise.
	 */
	public static boolean isOn() {
		return enabled;
	}
}