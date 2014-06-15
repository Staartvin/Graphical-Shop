package me.staartvin.plugins.graphicalshop.gui;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.plugins.graphicalshop.GraphicalShop;
import me.staartvin.plugins.graphicalshop.extra.requirements.Requirement;
import me.staartvin.plugins.graphicalshop.extra.results.Result;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * A class made to create icon menus. This is used to create the shop
 * interfaces. It's a little bit changed so that it fits Graphical Shop
 * <p>
 * Date created: 16:06:12 9 jun. 2014
 * 
 * @author nisovin
 * 
 */
public class IconMenu implements Listener {

	private String name;
	private int size;
	private OptionClickEventHandler handler;
	private Plugin plugin;

	private String[] optionNames;
	private SlotData[] optionIcons;

	public IconMenu(String name, int size, OptionClickEventHandler handler,
			Plugin plugin) {
		this.name = name;
		this.size = size;
		this.handler = handler;
		this.plugin = plugin;
		this.optionNames = new String[size];
		this.optionIcons = new SlotData[size];
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Set slot information
	 * 
	 * @param position slot id
	 * @param icon itemStack to show
	 * @param name name of the itemstack to show
	 * @param info lore of the itemstack
	 * @param reqs requirements for this slot (to be clicked) <b>Can be null</b>
	 * @param res results for this slot (when clicked) <b>Can be null</b>
	 * @return IconMenu with newly updated slot
	 */
	public IconMenu setOption(int position, ItemStack icon, String name,
			List<String> info, List<Requirement> reqs, List<Result> res) {
		optionNames[position] = name;

		// Fix lists if they're null
		if (reqs == null) {
			reqs = new ArrayList<Requirement>();
		}

		if (res == null) {
			res = new ArrayList<Result>();
		}

		ItemStack item = setItemNameAndLore(icon, name, info);
		optionIcons[position] = new SlotData(item, reqs, res);
		return this;
	}

	public void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++) {
			if (optionIcons[i] != null) {
				inventory.setItem(i, optionIcons[i].getItemStack());
			}
		}
		player.openInventory(inventory);
	}

	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
		plugin = null;
		optionNames = null;
		optionIcons = null;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if (slot >= 0 && slot < size && optionNames[slot] != null) {
				Plugin plugin = this.plugin;
				OptionClickEvent e = new OptionClickEvent(
						(Player) event.getWhoClicked(), slot,
						optionNames[slot], name, optionIcons[slot]);
				handler.onOptionClick(e);
				if (e.willClose()) {
					final Player p = (Player) event.getWhoClicked();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
							new Runnable() {
								public void run() {
									p.closeInventory();
								}
							}, 1);
				}
				if (e.willDestroy()) {
					destroy();
				}
			}
		}
	}

	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private Player player;
		private int position;
		private String name, inventoryName;
		private boolean close;
		private boolean destroy;
		private SlotData slotData;

		public OptionClickEvent(Player player, int position, String name,
				String inventoryName, SlotData data) {
			this.player = player;
			this.position = position;
			this.name = name;
			this.inventoryName = inventoryName;
			this.close = true;
			this.destroy = false;
			this.slotData = data;
		}

		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}

		public boolean willClose() {
			return close;
		}

		public boolean willDestroy() {
			return destroy;
		}

		public void setWillClose(boolean close) {
			this.close = close;
		}

		public void setWillDestroy(boolean destroy) {
			this.destroy = destroy;
		}

		public ItemStack getItemStack() {
			return slotData.getItemStack();
		}

		public SlotData getSlotData() {
			return slotData;
		}

		public int getPageNumber() {
			// SHOP - <shop type> - <page number>
			String[] array = ChatColor.stripColor(inventoryName).split("-");

			String pageString = array[2].toLowerCase();

			// Remove 'page'
			pageString = pageString.replace("page", "").trim();

			int pageNumber = Integer.parseInt(pageString);

			return pageNumber;
		}

		public String getShopType() {
			// SHOP - <shop type> - <page number>
			String[] array = ChatColor.stripColor(inventoryName).split("-");

			String shopName = array[1].trim().toLowerCase();

			return shopName;
		}

		public GraphicalShop getPlugin() {
			Plugin plugin = Bukkit.getServer().getPluginManager()
					.getPlugin("Graphical_Shop");

			return (GraphicalShop) plugin;
		}
	}

	private ItemStack setItemNameAndLore(ItemStack item, String name,
			List<String> lore) {
		ItemMeta im = item.getItemMeta();

		im.setDisplayName(name);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}

}