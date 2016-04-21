package mitty.asset;

import static mitty.util.Out.decimal;

import mitty.market.MarketTicker;
import mitty.trade.TradeJournal;

//Facade for Portfolio entry access.
public class Portfolio {

	String accountID;
	public Portfolio(String accountID) {
		this.accountID = accountID;
	}


	MarketTicker ticker = MarketTicker.instance();

	public double getAvgCostPrice(String symbol) {
		PortfolioEntry entry = PortfolioEntry.findByPK(symbol);
		if (entry == null) {
			throw new EntityNotFoundException();
		} else {

			return entry.avgPrice();
		}
	}

	public void sellAll(String symbol) {
		PortfolioEntry entry = PortfolioEntry.findByPK(symbol);
		if (entry != null) {
		sell(symbol, entry.getNumber());
		}
	}

	private MoneyMarket getMoneyMarket(){
		return MoneyMarket.moneyMarketForAccount(accountID);
	}
	public boolean buy(String symbol, int numberofstock) {
		ticker.addStock(symbol);

		double stockPrice = ticker.getQuote(symbol);
		double buyCost = numberofstock * stockPrice;
		if (getMoneyMarket().getBalance() < buyCost) {
			return false;
		}

		PortfolioEntry hold = PortfolioEntry.findByPK(symbol);
		if (hold == null) {
			hold = new PortfolioEntry();
		}
		hold.setSymbol(symbol);
		hold.setNumber(hold.getNumber()+ numberofstock);
		hold.setCost(hold.getCost() + buyCost);
		hold.store();
		getMoneyMarket().take(buyCost);
		TradeJournal.instance().addEntry(symbol, stockPrice, numberofstock, 0);
		return true;

	}

	public double inhold(String symbol) {
		PortfolioEntry entry = PortfolioEntry.findByPK(symbol);
		if (entry != null)
			return entry.getNumber();
		else
			return 0;
	}

	public boolean sell(String symbol, int numberofstock) {
		double inhold = inhold(symbol);
		if (inhold >= numberofstock) {
			PortfolioEntry hold = PortfolioEntry.findByPK(symbol);
			double stockPrice = ticker.getQuote(symbol);

			double valueToReduce = numberofstock * hold.avgPrice();
			hold.setNumber(hold.getNumber() -numberofstock);
			hold.setCost(hold.getCost() -valueToReduce);

			Double totalSale = numberofstock * stockPrice;
			getMoneyMarket().deposit(totalSale);

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
		for ( PortfolioEntry stockOnHold : PortfolioEntry.getAll()) {
			value += ticker.getQuote(stockOnHold.getSymbol()) * stockOnHold.getNumber();
		}
		return value;
	}

	public String toString() {

		String txt;
		txt = "PORTFOLIO { \n";
		for (PortfolioEntry entry : PortfolioEntry.getAll()) {
			txt += "symbol=" + entry.getSymbol() + " : " + "no.=" + decimal(entry.getNumber()) + " : " + "cost="
					+ decimal(entry.avgPrice()) + " : " + "current="
					+ decimal(MarketTicker.instance().getQuote(entry.getSymbol())) + "\n";
		}
		txt += "Total portfolio value : " + decimal(currentValue()) + "}\n";

		return txt;
	}
}

class EntityNotFoundException extends RuntimeException
{
       	
}
