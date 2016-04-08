package mitty.asset;

import static mitty.util.Out.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mitty.market.MarketTicker;
import mitty.trade.TradeJournal;

public class Portfolio {

	public Portfolio(MoneyMarket moneyMarket) {
		super();
		this.moneyMarket = moneyMarket;
		setup();
	}

	void setup() {
		List<PortfolioEntry> portFolioEntries = PortfolioEntry.getAll();
		for (PortfolioEntry entry : portFolioEntries) {
			portfolio.put(entry.getSymbol(), entry);
		}
	}

	MoneyMarket moneyMarket;

	Map<String, PortfolioEntry> portfolio = new HashMap<String, PortfolioEntry>();
	MarketTicker ticker = MarketTicker.instance();

	public double getAvgCostPrice(String symbol) {
		return portfolio.get(symbol).avgPrice();
	}

	public void sellAll(String symbol) {
		sell(symbol, portfolio.get(symbol).getNumber());
	}

	public boolean buy(String symbol, int numberofstock) {
		ticker.addStock(symbol);

		double stockPrice = ticker.getQuote(symbol);
		double buyCost = numberofstock * stockPrice;
		if (moneyMarket.getBalance() < buyCost) {
			return false;
		}

		PortfolioEntry hold = portfolio.get(symbol);
		if (hold == null) {
			hold = new PortfolioEntry();
		}
		hold.setSymbol(symbol);
		hold.setNumber(hold.getNumber()+ numberofstock);
		hold.setCost(hold.getCost() + buyCost);
		portfolio.put(symbol, hold);
		hold.store();
		moneyMarket.take(buyCost);
		TradeJournal.instance().addEntry(symbol, stockPrice, numberofstock, 0);
		return true;

	}

	double inhold(String symbol) {
		if (portfolio.get(symbol) != null)
			return portfolio.get(symbol).getNumber();
		else
			return 0;
	}

	public boolean sell(String symbol, int numberofstock) {
		double inhold = inhold(symbol);
		if (inhold >= numberofstock) {
			PortfolioEntry hold = portfolio.get(symbol);
			double stockPrice = ticker.getQuote(symbol);

			double valueToReduce = numberofstock * hold.avgPrice();
			hold.setNumber(hold.getNumber() -numberofstock);
			hold.setCost(hold.getCost() -valueToReduce);

			Double totalSale = numberofstock * stockPrice;
			moneyMarket.deposit(totalSale);

			portfolio.put(symbol, hold);
			hold.store();

			Double gain = totalSale - valueToReduce;

			TradeJournal.instance().addEntry(symbol, stockPrice, -numberofstock, gain);

			return true;
		} else {
			return false;
		}
	}

	public double currentValue() {
		double value = 0;
		for (Map.Entry<String, PortfolioEntry> stockOnHold : portfolio.entrySet()) {
			value += ticker.getQuote(stockOnHold.getKey()) * stockOnHold.getValue().getNumber();
		}
		return value;
	}

	public String toString() {

		String txt;
		txt = "Portfolio \n";
		for (Map.Entry<String, PortfolioEntry> entry : portfolio.entrySet()) {
			txt += "symbol="+entry.getKey() + " : " + "no.="+decimal(entry.getValue().getNumber()) +" : "+ "cost="+ decimal(entry.getValue().avgPrice()) + " : " + "current="+ decimal(MarketTicker.instance().getQuote(entry.getKey()))+ "\n";
		}
		txt += "Total portfolio value : " + decimal(currentValue()) + "\n";

		return txt;
	}
}
