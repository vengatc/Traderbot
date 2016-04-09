package mitty.statergy;

import static mitty.util.Out.*;

import mitty.asset.Assets;

// books profit over a percentage.

public class ProfitBooker extends TradeStatergyImpl {
	double sellTreshold;

	public ProfitBooker(double sellTreshold, String symbol) {
		super(symbol);
		this.sellTreshold = sellTreshold;
	}

	@Override
	public void execute() {

		try {
			decision = "ProfitBooker statergy : " + symbol;

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
