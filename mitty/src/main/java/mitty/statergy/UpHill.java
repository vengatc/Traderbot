package mitty.statergy;

import mitty.asset.Assets;
import mitty.market.MarketTicker;

import static mitty.util.Out.*;

//climber prevents losses.

public class UpHill extends TradeStatergyImpl {
	
	String name = "uphill";

	double downTreshold;
	double stockPrice;

	public UpHill(double downTreshold, String symbol) {
		super(symbol);
		this.downTreshold = downTreshold;
	}

	@Override
	public void execute() {

		try {
			decision = "Uphill statergy : " + symbol;

			if (!isActive()) {
				return;
			}
			this.stockPrice = Assets.instance().getPortfolio().getAvgCostPrice(symbol);


			double pricediff = ticker.getQuote(symbol) - stockPrice;
			double percentdiff = ((Math.abs(pricediff) / stockPrice) * 100);
			if (pricediff < 0 && percentdiff >= downTreshold) {
				decision += " [Decided to sell]";
				Assets.instance().getPortfolio().sellAll(symbol);

			} else {
				if (pricediff > 0) {
					stockPrice = ticker.getQuote(symbol);
				}
				decision += " [Decided to trail]";

			}
			decision += " [Where on Hill : " + decimal(stockPrice) + "]";
		} finally {
			System.out.println(decision);
		}
	}

}
