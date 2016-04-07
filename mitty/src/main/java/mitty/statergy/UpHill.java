package mitty.statergy;

import mitty.asset.Assets;
import mitty.market.MarketTicker;

import static mitty.util.Out.*;


//climber prevents losses.

public class UpHill implements TradeStatergy {
	double downTreshold;
	String symbol;
	double stockPrice;
	private MarketTicker ticker = MarketTicker.instance();

	public UpHill(double downTreshold, String symbol) {
		super();
		this.downTreshold = downTreshold;
		this.symbol = symbol;
		this.stockPrice = Assets.instance().getPortfolio().getAvgCostPrice(symbol);
	}

	@Override
	public void execute() {
		
		System.out.println("Checking UpHill statergy for :" + symbol);
		double pricediff = ticker.getQuote(symbol) - stockPrice;
		double percentdiff = ((Math.abs(pricediff) / stockPrice) * 100);
		if (pricediff < 0 && percentdiff >= downTreshold) {
			System.out.println("Decided to sell");
			Assets.instance().getPortfolio().sellAll(symbol);
			
		} else {
			if(pricediff >0){
			stockPrice = ticker.getQuote(symbol);
			}
			System.out.println("Decided to trail");

		}
		
		System.out.println("Where on Hill: " + df.format(stockPrice));
		
	}

}
