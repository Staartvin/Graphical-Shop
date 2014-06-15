package me.staartvin.plugins.graphicalshop.dependencies;

import java.util.HashMap;

import me.staartvin.plugins.graphicalshop.GraphicalShop;
import me.staartvin.plugins.graphicalshop.dependencies.hooks.VaultDependency;

/**
 * This class handles all connections with dependencies.
 * <p>
 * Date created: 14:38:09 13 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class DependencyManager {

	private GraphicalShop plugin;

	public DependencyManager(GraphicalShop plugin) {
		this.plugin = plugin;

		dependencies.put(dependency.VAULT, new VaultDependency(plugin));
	}

	public enum dependency {
		VAULT
	};

	private HashMap<dependency, Dependency> dependencies = new HashMap<dependency, Dependency>();

	public void loadDependencies() {

		plugin.getLogger().info(
				"---------[GraphicalShop Dependencies]---------");
		plugin.getLogger().info("Searching dependencies...");

		// Load all dependencies
		for (Dependency dependency : dependencies.values()) {
			dependency.setup();
		}

		plugin.getLogger().info(
				"---------[GraphicalShop Dependencies]---------");
	}

	public Dependency getDependency(dependency dependency) {
		return dependencies.get(dependency);
	}

}
