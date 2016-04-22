package mitty.statergy;

import static mitty.util.Out.*;

import mitty.asset.Assets;
import mitty.asset.StatergyEntry;

// books profit over a percentage.

public class ProfitBooker extends TradeStatergyImpl {
	
	public static final String SELLTRESHOLD="selltreshold";
	
	double sellTreshold;

	
	public static  ProfitBooker getinstance(StatergyEntry entry){
		return new ProfitBooker(entry.getFields().get(SELLTRESHOLD), entry.getSymbol());
	}
	public ProfitBooker(double sellTreshold, String symbol) {
		
		super(symbol,ProfitBooker.class.getName());
		StatergyEntry statrgyEntry = StatergyEntry.findByPK(StatergyEntry.makeKey(symbol,name));
		if(statrgyEntry == null){
			statrgyEntry = new StatergyEntry(symbol,name);
			statrgyEntry.getFields().put(SELLTRESHOLD,sellTreshold);
			statrgyEntry.store();
			
		}
		
		this.sellTreshold = sellTreshold;
	}

	@Override
	public void execute() {

		try {
			decision = currentTime()+"ProfitBooker statergy : " + symbol;

			if (!isActive()) {
				return;
			}
			double costPrice = Assets.instance().getPortfolio().getAvgCostPrice(symbol);

			double pricediff = ticker.getQuote(symbol) - costPrice;
			double percentdiff = ((Math.abs(pricediff) / costPrice) * 100);

			if (pricediff > 0 && percentdiff >= sellTreshold) {
				decision += " [Decided to sell]";
				Assets.instance().getPortfolio().sellAll(symbol);

			} else {
				if (pricediff < 0) {
					percentdiff = 0 - percentdiff;
				}
				decision += " [Decided to wait]";
			}

			decision += " [Current Profit percentage:" + decimal(percentdiff) + "]";
		} finally {
			System.out.println(decision);
		}
	}

}
