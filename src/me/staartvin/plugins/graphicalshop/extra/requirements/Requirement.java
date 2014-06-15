package me.staartvin.plugins.graphicalshop.extra.requirements;

import java.util.List;

import me.staartvin.plugins.graphicalshop.GraphicalShop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A simple requirement to be example
 * <p>
 * Date created:  20:50:31
 * 10 jun. 2014
 * @author Staartvin
 *
 */
public abstract class Requirement {

	private String menuType;
	private String iconName;
	
	/**
	 * Set options for this requirement
	 * @param args Arguments to get the options for.
	 */
	public abstract void setOptions(String... args);
	
	/**
	 * Check to see if player meets this requirement.
	 * @param player Player to check.
	 * @return true if this player meets requirement, false otherwise.
	 */
	public abstract boolean meetsRequirement(Player player);
	
	public GraphicalShop getPlugin() {
		return (GraphicalShop) Bukkit.getPluginManager().getPlugin("Graphical_Shop");
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Get a description of this requirement.
	 * <br>Useful for the lore
	 * @return a one line description of this requirement.
	 */
	public abstract List<String> getDescription();
	
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	
	public String getIconName() {
		return iconName;
	}
	
	public String getMenuType() {
		return menuType;
	}
}
