package mitty.statergy;

import mitty.asset.Assets;
import mitty.market.MarketTicker;

abstract public class TradeStatergyImpl implements TradeStatergy {

	protected String decision = "";
	protected boolean isActive;
	protected String symbol;
	protected MarketTicker ticker = MarketTicker.instance();
    protected String name;

	public TradeStatergyImpl(String symbol,String name)
	{
		this.symbol=symbol;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public String getDecision() {
		// TODO Auto-generated method stub
		return decision;
	}


	@Override
	public boolean isActive() {
		if (Assets.instance().getPortfolio().inhold(symbol) == 0) {
			decision +=" [inactive]";
			isActive = false;

		} else {
			isActive = true;
		}

		return isActive;

	}
    
}
