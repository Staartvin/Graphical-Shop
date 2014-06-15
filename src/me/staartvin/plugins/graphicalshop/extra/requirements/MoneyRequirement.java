package me.staartvin.plugins.graphicalshop.extra.requirements;

import java.util.Arrays;
import java.util.List;

import me.staartvin.plugins.graphicalshop.dependencies.hooks.VaultDependency;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Requirement that checks for amount of money
 * <p>
 * Date created:  20:55:28
 * 10 jun. 2014
 * @author Staartvin
 *
 */
public class MoneyRequirement extends Requirement {

	
	private double money = 0.0d;

	
	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.requirements.Requirement#setOptions(java.lang.String[])
	 */
	@Override
	public void setOptions(String... args) {
		// TODO Auto-generated method stub
		// arg[0] is money as double
		if (args.length > 0) {
			money = Double.parseDouble(args[0]);
		}
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.requirements.Requirement#meetsRequirement(org.bukkit.entity.Player)
	 */
	@Override
	public boolean meetsRequirement(Player player) {
		// TODO Auto-generated method stub
		
		OfflinePlayer p = this.getPlugin().getServer().getOfflinePlayer(player.getUniqueId());
		
		return VaultDependency.economy.has(p, money);
	}

	/* (non-Javadoc)
	 * @see me.staartvin.plugins.graphicalshop.extra.requirements.Requirement#getDescription()
	 */
	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		
		String moneyType = VaultDependency.economy.currencyNamePlural();
		
		if (moneyType.trim().equals("")) {
			moneyType = "money";
		}
		
		return Arrays.asList(ChatColor.RED + "You require " + money + " " + moneyType);
	}

}
