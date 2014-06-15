package me.staartvin.plugins.graphicalshop.extra.results;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * Perform commands as server console
 * <p>
 * Date created: 20:56:49 15 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class CommandsResult extends Result {

	private List<String> commands = new ArrayList<String>();

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String... args) {

		for (String arg : args) {

			commands.add(arg);
		}
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#performResult(org.bukkit.entity.Player)
	 */
	@Override
	public boolean performResult(Player player) {

		for (String command: commands) {
			command = command.replace("%player%", player.getName());
			
			this.getPlugin().getServer().dispatchCommand(this.getPlugin().getServer().getConsoleSender(), command);
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#getDescription()
	 */
	@Override
	public List<String> getDescription() {
		List<String> messages = new ArrayList<String>();

		for (String command: commands) {
			String message = "/" + command;
			
			messages.add(message);
		}

		return messages;
	}

}
