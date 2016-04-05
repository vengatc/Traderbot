package mitty.asset;

import static mitty.util.Out.df;

import java.util.HashMap;
import java.util.Map;

import mitty.market.MarketTicker;
import mitty.trade.TradeJournal;

public class Portfolio {

	public Portfolio(Assets assets) {
		super();
		this.assets = assets;
	}

	Assets assets;

	public class StockHold {
		int number;
		double cost;

		double avgPrice() {
			return cost / number;
		}

	}

	Map<String, StockHold> portfolio = new HashMap<String, StockHold>();
	MarketTicker ticker = MarketTicker.instance();

	public double getAvgCostPrice(String symbol) {
		return portfolio.get(symbol).avgPrice();
	}

	public void sellAll(String symbol) {
		sell(symbol, portfolio.get(symbol).number);
	}

	public boolean buy(String symbol, int numberofstock) {
		ticker.addStock(symbol);

		double stockPrice = ticker.getQuote(symbol);
		double buyCost = numberofstock * stockPrice;
		if (assets.getMoneyMarket().getBalance() < buyCost) {
			return false;
		}

		StockHold hold = portfolio.get(symbol);
		if (hold == null) {
			hold = new StockHold();
		}

		hold.number += numberofstock;
		hold.cost += buyCost;
		portfolio.put(symbol, hold);
		assets.getMoneyMarket().take(buyCost);
		TradeJournal.instance().addEntry(symbol, stockPrice, numberofstock, 0);
		return true;

	}

	double inhold(String symbol) {
		if (portfolio.get(symbol) != null)
			return portfolio.get(symbol).number;
		else
			return 0;
	}

	public boolean sell(String symbol, int numberofstock) {
		double inhold = inhold(symbol);
		if (inhold >= numberofstock) {
			StockHold hold = portfolio.get(symbol);
			double stockPrice = ticker.getQuote(symbol);

			double valueToReduce = numberofstock * hold.avgPrice();
			hold.number -= numberofstock;
			hold.cost -= valueToReduce;

			Double totalSale = numberofstock * stockPrice;
			assets.getMoneyMarket().deposit(totalSale);

			portfolio.put(symbol, hold);

			Double gain = totalSale - valueToReduce;

			TradeJournal.instance().addEntry(symbol, stockPrice, -numberofstock, gain);

			return true;
		} else {
			return false;
		}
	}

	public double currentValue() {
		double value = 0;
		for (Map.Entry<String, StockHold> stockOnHold : portfolio.entrySet()) {
			value += ticker.getQuote(stockOnHold.getKey()) * stockOnHold.getValue().number;
		}
		return value;
	}

	public String toString() {

		String txt;
		txt = "Portfolio \n";
		for (Map.Entry<String, StockHold> entry : portfolio.entrySet()) {
			txt += entry.getKey() + ":" + df.format(entry.getValue().number) + "\n";
		}
		txt += "Total portfolio value : " + df.format(currentValue()) + "\n";

		return txt;
	}
}
