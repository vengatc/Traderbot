package mitty.trade;

import java.util.Date;


//Journal entry

public class TradeEntry {

	
	public TradeEntry(String symbol, Date date, double price, int quanity,double gain) {
		super();
		this.date = date;
		this.symbol = symbol;
		this.price = price;
		this.gain = gain; 
		this.quantity = quanity;
		
		
	}
	String symbol;
	Date date;
	double price;
	double avgCostprice;
	double gain;
	int quantity;
   
	
	public String toString()
	{
		String printString = symbol+ ":"+ date+":"+price+":"+quantity+":"+gain;
		return printString;
	}
}
