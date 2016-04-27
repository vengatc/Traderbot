package mitty.asset;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

import mitty.db.StorageManager;

import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class StatergyEntry  {

	
	@PrimaryKey
    private String statergyKey;
	
	public String getStatergyKey() {
		return statergyKey;
	}
	public void setStatergyKey(String statergyKey) {
		this.statergyKey = statergyKey;
	}
	
	public static  String makeKey(String symbol, String statergy){
		return symbol+","+statergy;
	}
	
	private String symbol;
	
	public StatergyEntry()
	{
		
	}
	public StatergyEntry(String symbol,String statergy){
		this.symbol=symbol;
		this.statergy=statergy;
		statergyKey = makeKey(symbol, statergy);
	}
	
	
	//@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	//private String accountID;
	
    private String statergy;
		
	
	private Map<String,Double> fields = new TreeMap<String,Double>();

	 
	public void store() {
		StorageManager.instance().getAccessor().statergyEntryBySymbol.put(this);
		StorageManager.instance().close();
		
	}
	public static StatergyEntry findByPK(String primaryKey) {
		return StorageManager.instance().getAccessor().statergyEntryBySymbol.get(primaryKey);

	}

	public static List<StatergyEntry> getAll() throws DatabaseException {
		{

			List<StatergyEntry> list = new ArrayList<StatergyEntry>();
			// Get a cursor that will walk every
			// statergyEntry object in the store.
			EntityCursor<StatergyEntry> items = StorageManager.instance().getAccessor().statergyEntryBySymbol
					.entities();

			try {
				for (StatergyEntry item : items) {
					list.add(item);
				}
			} finally {
				items.close();
			}

			return list;
		}

	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getStatergy() {
		return statergy;
	}
	public void setStatergy(String statergy) {
		this.statergy = statergy;
	}
	public Map<String, Double> getFields() {
		return fields;
	}
	public void setFields(Map<String, Double> fields) {
		this.fields = fields;
	}


}