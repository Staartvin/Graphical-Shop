package me.staartvin.plugins.graphicalshop.config;

import java.util.Arrays;
import java.util.List;

import me.staartvin.plugins.graphicalshop.GraphicalShop;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Handles all retrieving and setting of data in the config.
 * <p>
 * Date created: 16:21:57 9 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class ConfigHandler {

	private GraphicalShop plugin;
	private FileConfiguration mainConfig;

	public ConfigHandler(GraphicalShop plugin) {
		this.plugin = plugin;

		mainConfig = plugin.getConfig();
	}

	public void loadConfig() {

		// Set header
		mainConfig.options().header(
				"Graphical Shop v" + plugin.getDescription().getVersion()
						+ " Config"
						+ "\nCategories is a list of menus that are available to the player."
						+ "\nA player will be able to do /gs <shop menu> and it will open the shop defined in the shops.yml"
						+ "\n\nIf 'show requirements' is true, all requirements for a slot will be displayed on the slot, the same for 'show results'"
						+ "\n"
						+ "\n"
						+ "\n"
						+ "\n"
						);

		mainConfig.addDefault("categories",
				Arrays.asList("buy", "shop", "sell"));
		mainConfig.addDefault("show requirements", true);
		mainConfig.addDefault("show results", true);

		// Save
		mainConfig.options().copyDefaults(true);
		plugin.saveConfig();
	}

	public List<String> getCategories() {
		return mainConfig.getStringList("categories");
	}
	
	public boolean doShowRequirements() {
		return mainConfig.getBoolean("show requirements", true);
	}
	
	public boolean doShowResults() {
		return mainConfig.getBoolean("show results", true);
	}
	
	public void reloadConfig() {
		plugin.reloadConfig();
		mainConfig = plugin.getConfig();
	}

}
