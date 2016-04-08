package mitty.asset;

import static mitty.util.Out.*;

import org.apache.log4j.Logger;


public class Assets {
	
	final static Logger logger = Logger.getLogger(Assets.class);

	private MoneyMarket moneyMarket;
	private Portfolio portfolio;
	private String accountID;

	//creates initially the balance will be zero.
	
	Assets(String accountID) {
		moneyMarket = MoneyMarket.getInstance(accountID);
		portfolio = new Portfolio(moneyMarket);
		this.accountID = accountID;

	}
	

	static Assets instance = new Assets("vengatc");

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public static Assets instance() {
		return instance;
	}

	public MoneyMarket getMoneyMarket() {
		return moneyMarket;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public double getTotalValue() {
		double cash = moneyMarket.getBalance();
		double stockvalue = portfolio.currentValue();
		return cash + stockvalue;
	}

	public String toString() {

		String txt;
		txt = moneyMarket.toString();
		txt += portfolio.toString();

		txt += "Total Assets=" + decimal(getTotalValue()) + "\n";

		return txt;
	}

}
