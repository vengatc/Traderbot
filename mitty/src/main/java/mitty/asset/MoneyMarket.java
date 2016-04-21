package mitty.asset;

import static mitty.util.Out.*;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.NotPersistent;

import mitty.db.StorageManager;

//money market account.

@Entity
public class MoneyMarket {
	@NotPersistent
	Assets assets;

	double balance;

	@PrimaryKey
	String accountID;

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
		store();
	}

	public synchronized double take(double amount) {
		try {
			if (balance >= amount) {
				balance -= amount;
				return amount;
			} else {
				amount = balance;
				balance = 0;
				return amount;
			}
		} finally {
			store();
		}

	}

	public synchronized void deposit(double amount) {

		balance += amount;
		store();
	}

	public String toString() {
		return "MONEYMARKET { (Balance):" + decimal(getBalance()) + "}\n";
	}

	// create if absent.
	static MoneyMarket moneyMarketForAccount(String accountID) {
		MoneyMarket moneyMarket = findByPK(accountID);
		if (moneyMarket == null) {
			moneyMarket = new MoneyMarket();
			moneyMarket.setAccountID(accountID);
			moneyMarket.store();
		}
		return moneyMarket;
	}

	public void store() {
		StorageManager.instance().getAccessor().moneyMarketByaccountID.put(this);
		StorageManager.instance().sync();
	}

	public static MoneyMarket findByPK(String primaryKey) {
		return StorageManager.instance().getAccessor().moneyMarketByaccountID.get(primaryKey);
	}

	public static List<MoneyMarket> getAll() throws DatabaseException {
		{

			List<MoneyMarket> list = new ArrayList<MoneyMarket>();
			// Get a cursor that will walk every
			// PortfolioEntry object in the store.
			EntityCursor<MoneyMarket> items = StorageManager.instance().getAccessor().moneyMarketByaccountID.entities();

			try {
				for (MoneyMarket item : items) {
					list.add(item);
				}
			} finally {
				items.close();
			}

			return list;
		}

	}
}
