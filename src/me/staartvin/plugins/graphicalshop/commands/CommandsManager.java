package me.staartvin.plugins.graphicalshop.commands;

import java.util.List;

import me.staartvin.plugins.graphicalshop.GraphicalShop;
import me.staartvin.plugins.graphicalshop.gui.IconMenu;
import me.staartvin.plugins.graphicalshop.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles all commands of Graphical Shop
 * <p>
 * Date created: 16:08:17 9 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class CommandsManager implements CommandExecutor {

	private GraphicalShop plugin;

	public CommandsManager(GraphicalShop plugin) {
		this.plugin = plugin;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.BLUE
					+ "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Developed by: "
					+ ChatColor.GRAY + plugin.getDescription().getAuthors());
			sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GRAY
					+ plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.YELLOW
					+ "Type /gs help for a list of commands.");
			return true;
		}

		if (args[0].equalsIgnoreCase("buy") || args[0].equalsIgnoreCase("sell")
				|| args[0].equalsIgnoreCase("shop")) {

			String action = args[0].toLowerCase();
			StringBuilder permission = new StringBuilder("graphicalshop.");

			if (action.equals("buy"))
				permission.append("buy");

			else if (action.equals("sell"))
				permission.append("sell");

			else
				permission.append("shop");

			if (!Util.hasPermission(permission.toString(), sender))
				return true;

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "I'm sorry, but I cannot show you a graphical interface!");
				return true;
			}

			List<IconMenu> menus = plugin.getMenuManager().getMenus(action);

			if (menus == null) {
				sender.sendMessage(ChatColor.RED
						+ "There doesn't seem to be a menu for that!");
				return true;
			}

			// Open the first one
			plugin.getMenuManager().getFrontMenu(action).open((Player) sender);
			return true;
		} else if (args[0].equalsIgnoreCase("reload")) {
			if (!Util.hasPermission("graphicalshop.reload", sender)) return true;
			
			plugin.getConfigHandler().reloadConfig();
			plugin.getShopsConfigHandler().reloadConfig();
			
			plugin.getMenuManager().loadMenus();

			sender.sendMessage(ChatColor.GREEN + "Successfully reloaded menus!");
			return true;
		}
		
		
		
		
		// Check for menus
		String menuType = args[0].toLowerCase();

		if (!plugin.getMenuManager().isValidMenu(menuType)) {
			sender.sendMessage(ChatColor.RED
					+ "There is no shopping menu called '" + menuType + "'!");
			return true;
		}

		if (!Util.hasPermission("graphicalshop." + menuType, sender))
			return true;

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "I'm sorry, but I cannot show you a graphical interface!");
			return true;
		}

		// Open menu
		plugin.getMenuManager().getFrontMenu(menuType).open((Player) sender);
		return true;

	}
}
