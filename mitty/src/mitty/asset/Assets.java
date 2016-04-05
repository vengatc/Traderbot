package mitty.asset;

import static mitty.util.Out.*;

public class Assets {
	private MoneyMarket moneyMarket;
	private Portfolio portfolio;

	Assets(double amount) {
		moneyMarket = new MoneyMarket(amount, this);
		portfolio = new Portfolio(this);

	}

	static Assets instance = new Assets(0);

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

		txt += "Total Assets=" + df.format(getTotalValue()) + "\n";

		return txt;
	}

}
