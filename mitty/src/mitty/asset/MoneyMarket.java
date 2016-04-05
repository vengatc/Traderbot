package mitty.asset;

import static mitty.util.Out.*;

//money market account.

public class MoneyMarket {
	Assets assets;
	double balance;
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public MoneyMarket(double amount, Assets assets) {
		balance = amount;
		this.assets = assets;
	}

	public synchronized double take(double amount) {
		if (balance >= amount) {
			balance -= amount;
			return amount;
		} else {
			amount = balance;
			balance = 0;
			return amount;
		}

	}

	public synchronized void deposit(double amount) {
		balance += amount;
	}
	
	public String toString()
	{		
		return "MoneyMarket Balance:" + df.format(getBalance()) + "\n";
	}
}
