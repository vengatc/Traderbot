package mitty.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeJournal {
	List<TradeEntry> journal = new ArrayList<TradeEntry>();
	
	static TradeJournal instance = new TradeJournal();
	
	
	public static TradeJournal instance()
	{
		return instance;
	}
	
	public void addEntry(String symbol, double price, int quanity,double gain)
	{
		
		TradeEntry entry = new TradeEntry(symbol,new Date(),  price,  quanity, gain);
		journal.add(entry);
	}
	
	void printJournal()
	{
		for(TradeEntry entry: journal)
		{
			System.out.println(entry.toString());
		}
	}
}
