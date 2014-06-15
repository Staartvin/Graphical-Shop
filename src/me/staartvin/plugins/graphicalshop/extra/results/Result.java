package me.staartvin.plugins.graphicalshop.extra.results;

import java.util.List;

import me.staartvin.plugins.graphicalshop.GraphicalShop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A simple result to be example
 * <p>
 * Date created:  20:50:31
 * 10 jun. 2014
 * @author Staartvin
 *
 */
public abstract class Result {

	private String menuType;
	private String iconName;
	
	/**
	 * Set options for this result
	 * @param args options to set.
	 */
	public abstract void setOptions(String... args);
	
	public abstract boolean performResult(Player player);
	
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
