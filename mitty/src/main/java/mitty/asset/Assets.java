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

		String txt = "ASSETS{\n";
		txt += moneyMarket.toString();
		txt += portfolio.toString();

		txt += "Total Assets=" + decimal(getTotalValue()) + "}\n";

		return txt;
	}

}
