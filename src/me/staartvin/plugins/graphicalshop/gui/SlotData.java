package me.staartvin.plugins.graphicalshop.gui;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.plugins.graphicalshop.extra.requirements.Requirement;
import me.staartvin.plugins.graphicalshop.extra.results.Result;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This represents a slot in a shop gui. It contains info about the requirements, the results and the itemstack.
 * <p>
 * Date created:  19:47:20
 * 12 jun. 2014
 * @author Staartvin
 *
 */
public class SlotData {

	private ItemStack item;
	private List<Requirement> requirements;
	private List<Result> results;
	
	public SlotData(ItemStack item, List<Requirement> reqs, List<Result> res) {
		this.item = item;
		this.requirements = reqs;
		this.results = res;
	}
	
	public SlotData(ItemStack item) {
		this.item = item;
		this.requirements = new ArrayList<Requirement>();
		this.results = new ArrayList<Result>();
	}
	
	public void setItemStack(ItemStack item) {
		this.item = item;
	}
	
	public void setRequirements(List<Requirement> reqs) {
		this.requirements = reqs;
	}
	
	public void setResults(List<Result> res) {
		this.results = res;
	}
	
	public ItemStack getItemStack() {
		return item;
	}
	
	public List<Requirement> getRequirements() {
		return requirements;
	}
	
	public List<Result> getResults() {
		return results;
	}
	
	public boolean meetsAllRequirements(Player player) {
		boolean failed = false;
		
		for (Requirement req: requirements) {
			if (!req.meetsRequirement(player)) {
				failed = true;
			}
		}
	
		return !failed;
	}
	
	public void performAllResults(Player player) {
		for (Result res: results) {
			res.performResult(player);
		}
	}
}
