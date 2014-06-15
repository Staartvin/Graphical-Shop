package me.staartvin.plugins.graphicalshop.dependencies;

import org.bukkit.plugin.Plugin;

/**
 * Represents a dependency.
 * <p>
 * Date created:  14:40:08
 * 13 jun. 2014
 * @author Staartvin
 *
 */
public abstract class Dependency {

	/**
	 * Get the main class of the dependency. <br>
	 * <b>Note that you still have to cast it to the correct plugin.</b>
	 * 
	 * @return main class of plugin, or null if not found.
	 */
	public abstract Plugin get();

	/**
	 * Setup the hook between this dependency and GraphicalShop
	 * 
	 * @return true if correctly setup, false otherwise.
	 */
	public abstract boolean setup();

	/**
	 * Check to see if this dependency is running on this server
	 * 
	 * @return true if it is, false otherwise.
	 */
	public abstract boolean isInstalled();

	/**
	 * Check whether GraphicalShop has hooked this dependency and thus can use it.
	 * 
	 * @return true if GraphicalShop hooked into it, false otherwise.
	 */
	public abstract boolean isAvailable();
}
