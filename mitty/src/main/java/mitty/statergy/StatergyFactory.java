package mitty.statergy;

// used to create trade stargergy for a stock can e made better latter, in the form of configuring the tresholds.

public class StatergyFactory{
	
	public static TradeStatergy createStatergy( String symbol,String statergy){
		switch (statergy){
		case "profitbooker":
		{
			return new ProfitBooker(10,symbol);
		}
		case "uphill":
		{
			return new UpHill(2,symbol);
		}
		default:{
			return null;
		}
		
		}
	}

}
