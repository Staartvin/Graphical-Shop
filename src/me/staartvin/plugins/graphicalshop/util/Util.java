package me.staartvin.plugins.graphicalshop.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

/**
 * Static methods to use for simplicity
 * <p>
 * Date created: 15:37:03 13 jun. 2014
 * 
 * <br>
 * Thanks to Ultimate_n00b from the Bukkit forums for the serialise and deserialise methods.
 * 
 * @author Staartvin
 * 
 */
public class Util {

	public static boolean hasPermission(final String permission,
			final CommandSender sender) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(ChatColor.RED + "You need (" + ChatColor.YELLOW
					+ permission + ChatColor.RED + ") to do this!");
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack ItemStackfromString(String input) {
		
		String[] array = input.split(";");
		
		if (array.length < 1) {
			throw new IllegalArgumentException("Invalid item: '" + input + "'!");
		}
		
		// args
		// itemid;amount;data
		ItemStack item = null;
		
		if (array.length > 2) {
			item = new ItemStack(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Short.parseShort(array[2]));
		} else {
			item = new ItemStack(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
		}
		
		return item;
	}
}
