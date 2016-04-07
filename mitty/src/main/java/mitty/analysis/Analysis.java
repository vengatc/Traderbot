package mitty.analysis;

import mitty.market.MarketTicker;

public class Analysis {
	
 	public static void main(String argv[])
	{
		MarketTicker.instance().addStock("GLD");
	}
}
