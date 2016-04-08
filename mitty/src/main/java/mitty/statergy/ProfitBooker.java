package mitty.statergy;

import static mitty.util.Out.*;

import mitty.asset.Assets;
import mitty.market.MarketTicker;

// books profit over a percentage.

public class ProfitBooker implements TradeStatergy {
	double sellTreshold;
	String symbol;
	private MarketTicker ticker = MarketTicker.instance();

	public ProfitBooker(double sellTreshold, String symbol) {
		super();
		this.sellTreshold = sellTreshold;
		this.symbol = symbol;
	}

	@Override
	public void execute() {
		
		System.out.println("Checking ProfitBooker statergy for :"+symbol);
		double costPrice = Assets.instance().getPortfolio().getAvgCostPrice(symbol);
		double pricediff = ticker.getQuote(symbol) - costPrice;
		double percentdiff = ((Math.abs(pricediff) / costPrice) * 100);
		
		if (pricediff > 0 && percentdiff >= sellTreshold) {
			System.out.println("Decided to sell");
			Assets.instance().getPortfolio().sellAll(symbol);
			
			
		} else {
			if (pricediff < 0) {
				percentdiff = 0 - percentdiff;
			}
			System.out.println("Decided to wait");
		}
		
		System.out.println("Current Profit Percent: " + decimal(percentdiff));
		
	}

}
