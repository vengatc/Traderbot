package mitty.statergy;

import mitty.asset.Assets;
import mitty.asset.StatergyEntry;

import static mitty.util.Out.*;

//climber prevents losses.

public class UpHill extends TradeStatergyImpl {
	
	public static final String DOWNTRESHOLD="downtreshold";
	public static final String WHEREONHILL="whereonhill";


	public UpHill(double downTreshold, String symbol,double whereOnHill) {
		super(symbol,UpHill.class.getName());
		
		StatergyEntry statrgyEntry = StatergyEntry.findByPK(StatergyEntry.makeKey(symbol,name));
		if(statrgyEntry == null){
			statrgyEntry = new StatergyEntry(symbol,name);
			statrgyEntry.getFields().put(DOWNTRESHOLD,downTreshold);
			statrgyEntry.getFields().put(WHEREONHILL,whereOnHill);
			statrgyEntry.store();
			
		}
		
	}

	
	static public UpHill getinstance(StatergyEntry entry){
		return new UpHill(entry.getFields().get(DOWNTRESHOLD), entry.getSymbol(), entry.getFields().get(WHEREONHILL));
	}
	
	@Override
	public void execute() {

		try {
			
			StatergyEntry entry = StatergyEntry.findByPK(StatergyEntry.makeKey(symbol,name));

			decision = "Uphill statergy : " + symbol;

			if (!isActive()) {
				return;
			}
			
			double whereOnHill=entry.getFields().get(WHEREONHILL);
			double downTreshold=entry.getFields().get(DOWNTRESHOLD);
			if (whereOnHill == 0) {
				whereOnHill = Assets.instance().getPortfolio().getAvgCostPrice(symbol);
			}

			double pricediff = ticker.getQuote(symbol) - whereOnHill;
			double percentdiff = ((Math.abs(pricediff) / whereOnHill) * 100);
			if (pricediff < 0 && percentdiff >= downTreshold) {
				decision += " [Decided to sell]";
				Assets.instance().getPortfolio().sellAll(symbol);

			} else {
				if (pricediff > 0) {
					whereOnHill = ticker.getQuote(symbol);
				}
				decision += " [Decided to trail]";

			}
			decision += " [Where on Hill : " + decimal(whereOnHill) + "]";
			entry.getFields().put(WHEREONHILL,whereOnHill );
			entry.store();

		} finally {
			System.out.println(decision);
			
		}
	}

}
