package me.staartvin.plugins.graphicalshop.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.staartvin.plugins.graphicalshop.GraphicalShop;
import me.staartvin.plugins.graphicalshop.extra.requirements.Requirement;
import me.staartvin.plugins.graphicalshop.extra.results.Result;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Stores all made menus
 * <p>
 * Date created: 16:28:01 9 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class MenuManager {

	private GraphicalShop plugin;

	public MenuManager(GraphicalShop plugin) {
		this.plugin = plugin;
	}

	private HashMap<String, List<IconMenu>> menus = new HashMap<String, List<IconMenu>>();

	public void loadMenus() {
		
		// Unregister all listeners when reloading
		for (Entry<String, List<IconMenu>> entry: menus.entrySet()) {
			for (IconMenu iconMenu: entry.getValue()) {
				iconMenu.destroy();
			}
		}
		
		// Clear hashmap
		menus.clear();
		
		// Loads all seperate menus.

		for (String menuType : plugin.getConfigHandler().getCategories()) {

			// Lower case
			menuType = menuType.toLowerCase();

			HashMap<Integer, ItemStack> items = plugin.getShopsConfigHandler()
					.getConfiguredItemsWithSlots(menuType);

			List<IconMenu> iconMenus = new ArrayList<IconMenu>();

			// Search for highest number
			int highestNumber = getHighestSlotNumber(items);

			if (highestNumber == 0) {
				highestNumber = 1;
			}

			// Divide by 26 and round up to get the amount of pages needed
			double pagesAmount = Math.ceil(highestNumber / 26d);

			// Create item icons for next and previous page
			ItemStack nextIcon = new ItemStack(Material.ARROW, 1);
			ItemStack previousIcon = nextIcon;
			// Add name
			ItemMeta nim = nextIcon.getItemMeta();
			ItemMeta pim = previousIcon.getItemMeta();

			nim.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					"&6Next page"));
			nim.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes(
					'&', "&3Click to go to the next page")));
			pim.setDisplayName(ChatColor.translateAlternateColorCodes('&',
					"&6Previous page"));
			pim.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes(
					'&', "&3Click to go to the previous page")));

			nextIcon.setItemMeta(nim);
			previousIcon.setItemMeta(pim);

			// Setup all menus
			for (int i = 0; i < pagesAmount; i++) {
				IconMenu menu = new IconMenu(
						ChatColor.translateAlternateColorCodes('&',
								"&6Shop - &5"
										+ menuType.toString().toUpperCase()
										+ " &6- Page " + i), 45,
						new OptionClickHandler(), plugin);

				// If there is a next page
				if ((i + 1) < pagesAmount) {

					// Add next button
					menu.setOption(44, nextIcon, nextIcon.getItemMeta()
							.getDisplayName(),
							nextIcon.getItemMeta().getLore(), null, null);
				}

				if (i != 0) {
					// Add previous button
					menu.setOption(36, previousIcon, previousIcon.getItemMeta()
							.getDisplayName(), previousIcon.getItemMeta()
							.getLore(), null, null);
				}

				iconMenus.add(menu);
			}

			// Start loading the items in
			for (Entry<Integer, ItemStack> entry : items.entrySet()) {

				ItemStack itemStack = entry.getValue();
				int slotNumber = entry.getKey().intValue();

				String iconName = plugin.getShopsConfigHandler().getIconName(
						menuType, slotNumber);

				List<Requirement> reqs = plugin.getShopsConfigHandler()
						.getRequirements(menuType, iconName);
				List<Result> ress = plugin.getShopsConfigHandler().getResults(
						menuType, iconName);

				double pageNumber = Math.floor(slotNumber / 27d);

				slotNumber = (int) (slotNumber - (27 * pageNumber));

				String displayName = itemStack.getType().toString()
						.toLowerCase().replace("_", " ");
				List<String> lore = new ArrayList<String>();

				if (itemStack.hasItemMeta()) {

					if (itemStack.getItemMeta().hasDisplayName()) {
						displayName = itemStack.getItemMeta().getDisplayName();
					}

					if (itemStack.getItemMeta().hasLore()) {

						List<String> listLore = itemStack.getItemMeta()
								.getLore();

						List<String> newLore = new ArrayList<String>();

						for (String loreLine : listLore) {
							newLore.add(ChatColor.translateAlternateColorCodes(
									'&', loreLine));
						}

						lore = newLore;
					}

				}

				if (plugin.getConfigHandler().doShowRequirements()) {

					// Empty line
					lore.add("");

					if (!reqs.isEmpty()) {
						lore.add("Requirements: ");

						// Add lore line for requirements
						for (Requirement req : reqs) {

							for (String desc : req.getDescription()) {
								lore.add(ChatColor
										.translateAlternateColorCodes('&', desc));
							}

						}
					} else {
						lore.add("No requirements");
					}

				}

				if (plugin.getConfigHandler().doShowResults()) {

					// Empty line
					lore.add("");

					if (!ress.isEmpty()) {
						lore.add("Results: ");

						for (Result res : ress) {

							for (String desc : res.getDescription()) {
								lore.add(ChatColor
										.translateAlternateColorCodes('&', desc));
							}

						}

					} else {
						lore.add("No results");
					}
				}

				iconMenus.get((int) pageNumber).setOption(slotNumber,
						itemStack, displayName, lore, reqs, ress);
			}

			menus.put(menuType, iconMenus);
		}

		plugin.getLogger().info("Menus loaded!");
	}

	public List<IconMenu> getMenus(String menuType) {
		return menus.get(menuType);
	}

	private int getHighestSlotNumber(HashMap<Integer, ItemStack> map) {
		int highestNumber = 0;

		for (Integer integer : map.keySet()) {
			if (integer.intValue() > highestNumber)
				highestNumber = integer;
		}

		return highestNumber;
	}

	public IconMenu getFrontMenu(String menuType) {
		return getMenus(menuType).get(0);
	}

	public IconMenu getSpecificMenu(String menuType, int position) {
		return getMenus(menuType).get(position);
	}

	public boolean isValidMenu(String menuType) {
		return menus.get(menuType) != null;
	}
}
