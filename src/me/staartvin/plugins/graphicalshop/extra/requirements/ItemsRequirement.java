package me.staartvin.plugins.graphicalshop.extra.requirements;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.plugins.graphicalshop.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Check to see if a player has all given items
 * <p>
 * Date created: 15:32:53 13 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class ItemsRequirement extends Requirement {

	private List<ItemStack> items = new ArrayList<ItemStack>();

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.requirements.Requirement#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String... args) {
		// Every argument is an item

		for (String arg : args) {

			ItemStack item;

			if (!arg.equalsIgnoreCase("this")) {
				item = Util.ItemStackfromString(arg);
			} else {
				// Equals this, thus return that item
				item = this.getPlugin().getShopsConfigHandler()
						.getItemStack(this.getMenuType(), this.getIconName());
			}

			items.add(item);
		}
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.requirements.Requirement#meetsRequirement(org.bukkit.entity.Player)
	 */
	@Override
	public boolean meetsRequirement(Player player) {
		boolean hasAllItems = true;

		for (ItemStack item : items) {
			if (!player.getInventory().containsAtLeast(item, item.getAmount())) {
				hasAllItems = false;
			}
		}

		return hasAllItems;
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.requirements.Requirement#getDescription()
	 */
	@Override
	public List<String> getDescription() {
		List<String> messages = new ArrayList<String>();

		for (ItemStack item : items) {
			StringBuilder message = new StringBuilder(ChatColor.RED
					+ "You need " + item.getAmount() + " "
					+ item.getType().toString().toLowerCase().replace("_", " "));

			if (item.getDurability() != 0) {
				message.append(" (data: " + item.getDurability() + ")");
			}

			messages.add(message.toString());
		}

		return messages;
	}

}
