package me.staartvin.plugins.graphicalshop;

import me.staartvin.plugins.graphicalshop.commands.CommandsManager;
import me.staartvin.plugins.graphicalshop.config.ConfigHandler;
import me.staartvin.plugins.graphicalshop.config.ShopsConfigHandler;
import me.staartvin.plugins.graphicalshop.dependencies.DependencyManager;
import me.staartvin.plugins.graphicalshop.extra.ExtraDataLoader;
import me.staartvin.plugins.graphicalshop.gui.MenuManager;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin will allow admins to set up a GUI-based shop, which allows
 * players to buy and sell items via a handy graphical interface.
 * <p>
 * Date created: 16:01:51 9 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class GraphicalShop extends JavaPlugin {

	private ConfigHandler configHandler = new ConfigHandler(this);
	private ShopsConfigHandler shopsConfigHandler = new ShopsConfigHandler(this);
	private MenuManager menuManager = new MenuManager(this);
	private ExtraDataLoader extraDataLoader = new ExtraDataLoader();
	private DependencyManager depManager = new DependencyManager(this);

	public void onEnable() {

		// Load reqs and results
		extraDataLoader.loadRequirements();
		extraDataLoader.loadResults();

		// Load config
		configHandler.loadConfig();
		
		// Load shops.yml
		shopsConfigHandler.createNewFile();
		shopsConfigHandler.loadConfig();

		// Register command
		this.getCommand("graphicalshop").setExecutor(new CommandsManager(this));

		// Load dependencies
		depManager.loadDependencies();

		// Load menus - it needs the dependencies
		menuManager.loadMenus();

		this.getLogger().info(
				this.getDescription().getFullName() + " has been enabled!");
	}

	public void onDisable() {
		this.getLogger().info(
				this.getDescription().getFullName() + " has been disabled!");
	}

	public ConfigHandler getConfigHandler() {
		return configHandler;
	}

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public ExtraDataLoader getExtraDataLoader() {
		return extraDataLoader;
	}

	public DependencyManager getDependencyManager() {
		return depManager;
	}
	
	public ShopsConfigHandler getShopsConfigHandler() {
		return shopsConfigHandler;
	}
}
