package mitty.analysis;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import mitty.db.StorageManager;

//@Entity
public class StockHistoryEntry  {

	//@PrimaryKey
    private String symbol;

	private Map<Long,Double> closes = new TreeMap<Long,Double>();
	
	private Map<Long,Double> volumes = new TreeMap<Long,Double>();

	private long snapsnotTime;

	/* 
	public void store() {
		StorageManager.instance().getAccessor().stockEntryBySymbol.put(this);
		StorageManager.instance().sync();
		
	}
	
	public static StockHistoryEntry findByPK(String primaryKey) {
		return StorageManager.instance().getAccessor().stockEntryBySymbol.get(primaryKey);

	}*/
	
	public long getSnapsnotTime() {
		return snapsnotTime;
	}

	public void setSnapsnotTime(long snapsnotTime) {
		this.snapsnotTime = snapsnotTime;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Map<Long, Double> getCloses() {
		return closes;
	}

	public void setCloses(Map<Long, Double> closequotes) {
		this.closes = closequotes;
	}

	public Map<Long, Double> getVolumes() {
		return volumes;
	}

	public void setVolumes(Map<Long, Double> volume) {
		this.volumes = volume;
	}

	
	public String toString()
	{
		String sym="",close="",volume="";
		 sym ="["+symbol+"]\n";
		 //close= "CLOSES:"+Arrays.toString(closes.entrySet().toArray())+"\n";
		 volume = "VOLUMES:"+Arrays.toString(volumes.entrySet().toArray());

		return sym+close+volume;
	}
	
	

}