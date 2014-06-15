package me.staartvin.plugins.graphicalshop.gui;

import me.staartvin.plugins.graphicalshop.gui.IconMenu.OptionClickEvent;
import me.staartvin.plugins.graphicalshop.gui.IconMenu.OptionClickEventHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Listener for when an item is clicked
 * <p>
 * Date created: 21:17:36 12 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class OptionClickHandler implements OptionClickEventHandler {

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.gui.IconMenu.OptionClickEventHandler#onOptionClick(me.staartvin.plugins.graphicalshop.gui.IconMenu.OptionClickEvent)
	 */
	@Override
	public void onOptionClick(final OptionClickEvent event) {
		// TODO Auto-generated method stub
		
		event.setWillClose(false);

		final Player player = event.getPlayer();
		SlotData data = event.getSlotData();

		// Wants to go to next page
		if (event.getPosition() == 44) {

			Bukkit.getScheduler().runTaskLater(event.getPlugin(),
					new Runnable() {
						public void run() {
							int pageNumber = event.getPageNumber();

							IconMenu nextMenu = event
									.getPlugin()
									.getMenuManager()
									.getSpecificMenu(event.getShopType(),
											(pageNumber + 1));

							nextMenu.open(player);
						}
					}, 2);

			event.setWillClose(true);
			return;
		} else if (event.getPosition() == 36) {
			// Previous button

			Bukkit.getScheduler().runTaskLater(event.getPlugin(),
					new Runnable() {
						public void run() {
							int pageNumber = event.getPageNumber();

							IconMenu nextMenu = event
									.getPlugin()
									.getMenuManager()
									.getSpecificMenu(event.getShopType(),
											(pageNumber - 1));

							nextMenu.open(player);
						}
					}, 2);

			event.setWillClose(true);
			return;

		}

		if (!data.meetsAllRequirements(player)) {
			player.sendMessage(ChatColor.RED
					+ "You didn't pass all requirements!");
			return;
		}
		
		
		// Passed all requirements, so perform result
		data.performAllResults(player);
	}

}
