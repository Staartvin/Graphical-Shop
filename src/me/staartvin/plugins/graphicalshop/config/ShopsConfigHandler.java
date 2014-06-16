package me.staartvin.plugins.graphicalshop.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import me.staartvin.plugins.graphicalshop.GraphicalShop;
import me.staartvin.plugins.graphicalshop.extra.ExtraDataLoader;
import me.staartvin.plugins.graphicalshop.extra.requirements.Requirement;
import me.staartvin.plugins.graphicalshop.extra.results.Result;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Class for the shops.yml config
 * <p>
 * Date created: 21:18:52 15 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class ShopsConfigHandler {

	private GraphicalShop plugin;
	private FileConfiguration shopsConfig;
	private File configFile;

	public ShopsConfigHandler(GraphicalShop plugin) {
		this.plugin = plugin;
	}

	public void createNewFile() {
		reloadConfig();
		saveConfig();
		loadConfig();

		plugin.getLogger().info("Loaded shops.yml");
	}

	@SuppressWarnings("deprecation")
	public void reloadConfig() {
		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), "shops.yml");
		}
		shopsConfig = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		final InputStream defConfigStream = plugin.getResource("shops.yml");
		if (defConfigStream != null) {
			final YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			shopsConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (shopsConfig == null) {
			this.reloadConfig();
		}
		return shopsConfig;
	}

	public void saveConfig() {
		if (shopsConfig == null || configFile == null) {
			return;
		}
		try {
			getConfig().save(configFile);
		} catch (final IOException ex) {
			plugin.getLogger().log(Level.SEVERE,
					"Could not save config to " + configFile, ex);
		}
	}

	public void loadConfig() {

		shopsConfig.options().header(
				"You can edit this file to change the layout of your shops");

		// Example shop item
		shopsConfig.addDefault("shop.icon 1.item id", 35);
		shopsConfig.addDefault("shop.icon 1.data", 14);
		shopsConfig.addDefault("shop.icon 1.amount", 2);
		shopsConfig.addDefault("shop.icon 1.slot", 0);

		// Example buy item
		shopsConfig.addDefault("buy.icon 1.item id", 276);
		// Not necessary -> config.addDefault("shop.icon 1.data", 0);
		shopsConfig.addDefault("buy.icon 1.amount", 1);
		shopsConfig.addDefault("buy.icon 1.slot", 0);
		shopsConfig.addDefault("buy.icon 1.enchantments",
				Arrays.asList("16;4", "19;2", "21;3"));
		shopsConfig.addDefault("buy.icon 1.display name",
				"&6Mystical sword of the iron dragon");
		shopsConfig.addDefault("buy.icon 1.lore", Arrays.asList(
				"&5This mystical sword is said to",
				"&5be crafted with a specific", "&5kind of iron.."));

		// Example sell item
		shopsConfig.addDefault("sell.icon 1.item id", 306);
		// Not necessary -> config.addDefault("shop.icon 1.data", 0);
		shopsConfig.addDefault("sell.icon 1.amount", 1);
		shopsConfig.addDefault("sell.icon 1.slot", 0);
		shopsConfig.addDefault("sell.icon 1.enchantments",
				Arrays.asList("34;2", "0;4"));
		shopsConfig.addDefault("sell.icon 1.display name", "&fA strong helmet");
		shopsConfig.addDefault("sell.icon 1.lore", Arrays.asList(
				"&4Quite a strong helmet,", "&3if I say so myself.."));

		// Set requirements and results
		shopsConfig.addDefault("shop.icon 1.requirements.money", 25.2);
		shopsConfig.addDefault("shop.icon 1.results.items",
				Arrays.asList("this"));
		shopsConfig.addDefault("shop.icon 1.results.money take", 25.2);

		shopsConfig.options().copyDefaults(true);
		saveConfig();
	}
	
	public List<ItemStack> getConfiguredItems(String menuType) {
		String prefix = menuType.toLowerCase();

		Set<String> icons = shopsConfig.getConfigurationSection(prefix).getKeys(
				false);

		List<ItemStack> items = new ArrayList<ItemStack>();

		for (String icon : icons) {
			items.add(getItemStack(menuType, icon));
		}

		return items;
	}

	@SuppressWarnings("deprecation")
	public ItemStack getItemStack(String menuType, String iconName) {
		String prefix = menuType.toLowerCase() + "." + iconName + ".";

		// Deserialise
		int itemid = shopsConfig.getInt(prefix + "item id", 0);
		short data = (short) shopsConfig.getInt(prefix + "data", 0);
		int amount = shopsConfig.getInt(prefix + "amount");

		List<String> sEnchantments = shopsConfig.getStringList(prefix
				+ "enchantments");
		String displayName = shopsConfig.getString(prefix + "display name", "");
		List<String> lore = shopsConfig.getStringList(prefix + "lore");

		ItemStack item = null;

		if (data != 0)
			item = new ItemStack(itemid, amount, data);
		else
			item = new ItemStack(itemid, amount);
		
		System.out.print("Meta: " + item.getItemMeta());
		System.out.print("Other meta: " + Bukkit.getItemFactory().getItemMeta(item.getType()));

		// Add enchantments
		if (!sEnchantments.isEmpty()) {
			for (String sEnchantment : sEnchantments) {
				String[] array = sEnchantment.split(";");
				
				System.out.print(sEnchantment);

				Enchantment enchantment = Enchantment.getById(Integer
						.parseInt(array[0]));
				
				System.out.print("Enchantment: " + enchantment);
				System.out.print("Level: " + Integer.parseInt(array[1]));

				item.addUnsafeEnchantment(enchantment,
						Integer.parseInt(array[1]));
			}
		}

		System.out.print("Other meta 2: " + Bukkit.getItemFactory().getItemMeta(item.getType()));
		
		ItemMeta meta = item.getItemMeta();

		// Set display name
		if (!displayName.isEmpty()) {
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					displayName));
		}

		// Add lore
		if (!lore.isEmpty()) {
			meta.setLore(lore);
		}

		// Replace meta with new meta
		item.setItemMeta(meta);

		return item;
	}

	public HashMap<Integer, ItemStack> getConfiguredItemsWithSlots(
			String menuType) {
		String prefix = menuType.toLowerCase();
		HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();

		ConfigurationSection section = shopsConfig
				.getConfigurationSection(prefix);

		if (section == null) {
			throw new NullPointerException("Shop menu '" + menuType
					+ "' is defined in categories, but not in the shops.yml!");
		}

		Set<String> icons = section.getKeys(false);

		for (String icon : icons) {
			ItemStack item = getItemStack(menuType, icon);

			items.put(getSlot(menuType, icon), item);
		}

		return items;
	}

	public int getSlot(String menuType, String iconName) {
		String prefix = menuType.toLowerCase();
		return shopsConfig.getInt(prefix + "." + iconName + ".slot");
	}
	
	public Set<String> getStringRequirements(String menuType, String iconName) {
		String prefix = menuType.toLowerCase();

		ConfigurationSection section = shopsConfig
				.getConfigurationSection(prefix + "." + iconName
						+ ".requirements");

		if (section == null) {
			return new HashSet<String>();
		}

		return section.getKeys(false);
	}

	public Set<String> getStringResults(String menuType, String iconName) {
		String prefix = menuType.toLowerCase();

		ConfigurationSection section = shopsConfig
				.getConfigurationSection(prefix + "." + iconName + ".results");

		if (section == null) {
			return new HashSet<String>();
		}

		return section.getKeys(false);
	}

	public String getIconName(String menuType, int realSlot) {
		String prefix = menuType.toLowerCase();

		Set<String> icons = shopsConfig.getConfigurationSection(prefix)
				.getKeys(false);

		for (String icon : icons) {
			int slot = this.getSlot(menuType, icon);

			if (slot == realSlot)
				return icon;
		}

		return null;
	}

	public List<Requirement> getRequirements(String menuType, String iconName) {

		Set<String> stringReqs = getStringRequirements(menuType, iconName);
		List<Requirement> reqs = new ArrayList<Requirement>();

		for (String sReq : stringReqs) {
			Requirement req = getRequirement(menuType, iconName, sReq);

			if (req != null) {
				reqs.add(req);
			}
		}

		return reqs;
	}

	public List<Result> getResults(String menuType, String iconName) {
		Set<String> stringRes = getStringResults(menuType, iconName);
		List<Result> ress = new ArrayList<Result>();

		for (String sRes : stringRes) {
			Result res = getResult(menuType, iconName, sRes);

			if (res != null) {
				ress.add(res);
			}
		}

		return ress;
	}

	public Requirement getRequirement(String menuType, String iconName,
			String requirementName) {
		String prefix = menuType.toLowerCase();

		Object object = shopsConfig.get(prefix + "." + iconName
				+ ".requirements." + requirementName);

		ExtraDataLoader eDL = plugin.getExtraDataLoader();

		Requirement req = eDL.createRequirement(requirementName);

		if (req == null)
			return null;

		req.setIconName(iconName);
		req.setMenuType(menuType);

		if (object instanceof List<?>) {
			List<?> list = (List<?>) object;

			String[] array = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i).toString();
			}

			req.setOptions(array);
		} else {
			req.setOptions(object.toString());
		}

		return req;
	}

	public Result getResult(String menuType, String iconName, String resultName) {
		String prefix = menuType.toLowerCase();

		Object object = shopsConfig.get(prefix + "." + iconName + ".results."
				+ resultName);

		ExtraDataLoader eDL = plugin.getExtraDataLoader();

		Result res = eDL.createResult(resultName);

		if (res == null)
			return null;

		res.setIconName(iconName);
		res.setMenuType(menuType);

		if (object instanceof List<?>) {
			List<?> list = (List<?>) object;

			String[] array = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i).toString();
			}

			res.setOptions(array);
		} else {
			res.setOptions(object.toString());
		}

		return res;
	}
}
