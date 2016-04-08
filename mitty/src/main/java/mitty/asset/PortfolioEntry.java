package mitty.asset;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

import mitty.db.StorageManager;

import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PortfolioEntry  {

	@PrimaryKey
	String symbol;
		
	/*
	@SecondaryKey(relate=MANY_TO_ONE)
    String accountID;
	

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}*/

	int number;
	double cost;

	double avgPrice() {
		if(number == 0 ) return 0;
		return cost / number;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	 
	public void store() {
		StorageManager.instance().getAccessor().portfolioEntryBySymbol.put(this);
		StorageManager.instance().flush();
		
	}
	public static PortfolioEntry findByPK(String primaryKey) {
		return StorageManager.instance().getAccessor().portfolioEntryBySymbol.get(primaryKey);

	}

	public static List<PortfolioEntry> getAll() throws DatabaseException {
		{

			List<PortfolioEntry> list = new ArrayList<PortfolioEntry>();
			// Get a cursor that will walk every
			// PortfolioEntry object in the store.
			EntityCursor<PortfolioEntry> items = StorageManager.instance().getAccessor().portfolioEntryBySymbol
					.entities();

			try {
				for (PortfolioEntry item : items) {
					list.add(item);
				}
			} finally {
				items.close();
			}

			return list;
		}

	}

}