package me.staartvin.plugins.graphicalshop.extra;

import java.util.HashMap;

import me.staartvin.plugins.graphicalshop.extra.requirements.ItemsRequirement;
import me.staartvin.plugins.graphicalshop.extra.requirements.MoneyRequirement;
import me.staartvin.plugins.graphicalshop.extra.requirements.Requirement;
import me.staartvin.plugins.graphicalshop.extra.results.CommandsResult;
import me.staartvin.plugins.graphicalshop.extra.results.ItemsResult;
import me.staartvin.plugins.graphicalshop.extra.results.MoneyGiveResult;
import me.staartvin.plugins.graphicalshop.extra.results.MoneyTakeResult;
import me.staartvin.plugins.graphicalshop.extra.results.Result;

/**
 * Loads all requirements and results
 * <p>
 * Date created: 20:25:27 12 jun. 2014
 * 
 * @author Staartvin
 * 
 */
public class ExtraDataLoader {

	private HashMap<String, Class<? extends Requirement>> requirements = new HashMap<String, Class<? extends Requirement>>();
	private HashMap<String, Class<? extends Result>> results = new HashMap<String, Class<? extends Result>>();

	public void loadResults() {
		results.put("items", ItemsResult.class);
		results.put("commands", CommandsResult.class);
		results.put("money take", MoneyTakeResult.class);
		results.put("money give", MoneyGiveResult.class);
	}

	public void loadRequirements() {
		requirements.put("money", MoneyRequirement.class);
		requirements.put("items", ItemsRequirement.class);
	}

	public Requirement createRequirement(String type) {

		Requirement req = null;

		Class<? extends Requirement> clazz = requirements.get(type);

		if (clazz != null) {
			try {
				req = clazz.newInstance();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return req;
	}

	public Result createResult(String type) {

		Result res = null;

		Class<? extends Result> clazz = results.get(type);

		if (clazz != null) {
			try {
				res = clazz.newInstance();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return res;
	}

}
