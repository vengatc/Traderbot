package mitty.asset;

import static mitty.util.Out.*;

import org.apache.log4j.Logger;


public class Assets {
	
	final static Logger logger = Logger.getLogger(Assets.class);

	private Portfolio portfolio;
	private String accountID;

	//creates initially the balance will be zero.
	
	Assets(String accountID) {
		portfolio = new Portfolio(accountID);
		this.accountID = accountID;

	}
	

	static Assets instance;

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public synchronized static  Assets instance() {
		if(instance==null){
			instance = new Assets("vengatc");
		}
		return instance;
	}
	
	public synchronized static void reset() {
		instance=null;
	}

	public MoneyMarket getMoneyMarket() {
		return MoneyMarket.moneyMarketForAccount(accountID);
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public double getTotalValue() {
		double cash = MoneyMarket.moneyMarketForAccount(accountID).getBalance();
		double stockvalue = portfolio.currentValue();
		return cash + stockvalue;
	}

	public String toString() {

		String txt = "ASSETS{\n";
		txt += MoneyMarket.moneyMarketForAccount(accountID).toString();
		txt += portfolio.toString();

		txt += "Total Assets=" + decimal(getTotalValue()) + "}\n";

		return txt;
	}

}
