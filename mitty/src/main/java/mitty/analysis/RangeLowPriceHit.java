package mitty.analysis;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import mitty.asset.StatergyEntry;
import mitty.market.MarketTicker;
import mitty.market.StockHistory;
import mitty.util.Out;

public class RangeLowPriceHit extends AnalysisBase {

	double days;
	double percentageclose;
	final static String PERCENTABOVELOW="%abovelow";
	final static String DAYS="days";
   
	
	
	
	public static  RangeLowPriceHit getinstance(StatergyEntry entry){
		return new RangeLowPriceHit(entry.getSymbol(),entry.getFields().get(DAYS),entry.getFields().get(PERCENTABOVELOW));
	}
	

	public RangeLowPriceHit(String symbol, double days, double percentageclose) {
		super(symbol, RangeLowPriceHit.class.getName());
		
		StatergyEntry statrgyEntry = StatergyEntry.findByPK(StatergyEntry.makeKey(symbol,name));
		if(statrgyEntry == null){
			statrgyEntry = new StatergyEntry(symbol,name);
			statrgyEntry.getFields().put(PERCENTABOVELOW,percentageclose);
			statrgyEntry.getFields().put(DAYS,days);

			statrgyEntry.store();
			
		}
		
		this.days = days;
		this.percentageclose = percentageclose;
		// TODO Auto-generated constructor stub
	}

	public RangeLowPriceHit(String symbol) {
		this(symbol, 100.0,1.0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		
		setHit(false);
		decision = currentTime() + "RangeLowPriceHit analysis : " + symbol;

		StockHistoryEntry history = StockHistory.getStockHistoryEntry(symbol);
		// checks if the 100days low was hit.
		TreeMap<Long, Double> closes = (TreeMap<Long, Double>) history.getCloses();
		Map<Long, Double> historyDays = lastXentries(closes, days);
		Double low = Collections.min(historyDays.values());
		Double high = Collections.max(historyDays.values());

		Double currentPrice = MarketTicker.instance().getQuote(symbol);
		Double precentageDifference = (Math.abs(currentPrice - low) / low) * 100;
		decision += "[ days=" + days + " minimum is=" + Out.decimal(low) + " current=" + Out.decimal(currentPrice) + "]";

		if (precentageDifference <= percentageclose) {
			decision += " [ HIT ]";
			setHit(true);
		} else {
			decision += " [ MISS ]";
		}
		System.out.println(decision);
		
	}

	TreeMap<Long, Double> lastXentries(TreeMap<Long, Double> closes, double size) {
		TreeMap<Long, Double> cutMap = new TreeMap<Long, Double>();
		for (int i = 0; i < size; i++) {
			Entry<Long, Double> entry = closes.pollLastEntry();
			cutMap.put(entry.getKey(), entry.getValue());
		}
		return cutMap;
	}

	public static void main(String argv[]) {
		RangeLowPriceHit la = new RangeLowPriceHit("GLD");
		la.execute();
		System.out.println(la.getDecision());
	}

}
