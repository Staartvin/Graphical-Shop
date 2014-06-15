package me.staartvin.plugins.graphicalshop.extra.results;

import java.util.Arrays;
import java.util.List;

import me.staartvin.plugins.graphicalshop.dependencies.hooks.VaultDependency;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Give items to a player
 * <p>
 * Date created: 20:56:49 15 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class MoneyTakeResult extends Result {

	private double money = 0.0d;

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String... args) {
		if (args.length > 0) {
			money = Double.parseDouble(args[0]);
		}
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#performResult(org.bukkit.entity.Player)
	 */
	@Override
	public boolean performResult(Player player) {

		OfflinePlayer p = this.getPlugin().getServer()
				.getOfflinePlayer(player.getUniqueId());

		EconomyResponse r = VaultDependency.economy.withdrawPlayer(p, money);

		return r.transactionSuccess();
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.results.Result#getDescription()
	 */
	@Override
	public List<String> getDescription() {
		String moneyType = VaultDependency.economy.currencyNamePlural();

		if (moneyType.trim().equals("")) {
			moneyType = "money";
		}

		return Arrays.asList(ChatColor.RED + "Take " + money + " " + moneyType);
	}
}
