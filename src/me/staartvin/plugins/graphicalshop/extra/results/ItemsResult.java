package me.staartvin.plugins.graphicalshop.extra.results;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.plugins.graphicalshop.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Give items to a player
 * <p>
 * Date created:  20:56:49
 * 15 jun. 2014
 * @author Staartvin
 *
 */
public class ItemsResult extends Result {

	private List<ItemStack> items = new ArrayList<ItemStack>();
	
	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String... args) {
	
		for (String arg : args) {

			ItemStack item;

			if (!arg.equalsIgnoreCase("this")) {
				item = Util.ItemStackfromString(arg);
			} else {
				// Equals this, thus return that item
				item = this.getPlugin().getShopsConfigHandler()
						.getItemStack(this.getMenuType(), this.getIconName());
			}

			if (item != null) {
				items.add(item);	
			}
		}
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#performResult(org.bukkit.entity.Player)
	 */
	@Override
	public boolean performResult(Player player) {
		
		for (ItemStack item: items) {
			if (player.getInventory().firstEmpty() < 0) {
				// No place in inventory, dropped on ground
				player.getWorld().dropItem(player.getLocation(), item);
			} else {
				// Put in inventory
				player.getInventory().addItem(item);
			}
		}
		
		return true;	
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#getDescription()
	 */
	@Override
	public List<String> getDescription() {
		List<String> messages = new ArrayList<String>();

		for (ItemStack item : items) {
			StringBuilder message = new StringBuilder(ChatColor.RED
					+ "You get " + item.getAmount() + " "
					+ item.getType().toString().toLowerCase().replace("_", " "));

			if (item.getDurability() != 0) {
				message.append(" (data: " + item.getDurability() + ")");
			}

			messages.add(message.toString());
		}

		return messages;
	}

}
