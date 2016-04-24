package mitty.statergy;

import java.util.HashSet;
import java.util.Set;

import mitty.analysis.RangeLowPriceHit;
import mitty.asset.StatergyEntry;
import mitty.market.MarketTicker;

// used to create trade stargergy for a stock can e made better latter, in the form of configuring the tresholds.

public class StatergyManager {

	public static TradeStatergy createStatergy(String symbol, String statergy) {

		// validating symbol
		if (MarketTicker.instance().getQuote(symbol) == 0)
			return null;
		switch (statergy) {
		case "profitbooker": {
			return new ProfitBooker(10, symbol);
		}
		case "uphill": {
			return new UpHill(2, symbol, 0);
		}
		case "rangelowhit":{
			return new RangeLowPriceHit(symbol);
		}
		default: {
			return null;
		}

		}
	}

	public static Set<TradeStatergy> getAllStatergy() {
		Set<TradeStatergy> statergySet = new HashSet<TradeStatergy>();

		for (StatergyEntry entry : StatergyEntry.getAll()) {
			
			System.out.println("######Statergy retrived ==" + entry.getStatergy());
			

			if (entry.getStatergy().equals(ProfitBooker.class.getName())) {

				statergySet.add(ProfitBooker.getinstance(entry));

			}

			if (entry.getStatergy().equals(UpHill.class.getName())) {

				statergySet.add(UpHill.getinstance(entry));

			}
			
			if (entry.getStatergy().equals(RangeLowPriceHit.class.getName())) {

				statergySet.add(RangeLowPriceHit.getinstance(entry));

			}

		}
		return statergySet;

	}

}
