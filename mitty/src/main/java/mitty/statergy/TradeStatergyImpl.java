package mitty.statergy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mitty.asset.Assets;
import mitty.market.MarketTicker;
import mitty.notification.SMSNotification;

abstract public class TradeStatergyImpl implements TradeStatergy {

	protected String decision = "";
	protected boolean isActive;
	protected String symbol;
	protected MarketTicker ticker = MarketTicker.instance();
    protected String name;
	protected boolean hit = false;

	
	protected abstract void process();

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
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isHit() {
		return hit;
	}
	public String currentTime(){
		Calendar cal = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
        SimpleDateFormat sdf = new SimpleDateFormat("(dd/MM HH:mm:ss) ");

        String strDate = sdf.format(cal.getTime());
        return strDate;
	}
	
	public void actedOnDecision(String action){
		SMSNotification.notify("4084313537@txt.att.net", action);
	}

	
	@Override
	public void execute() {
		process();
		
		if(isHit()){
			actedOnDecision(decision);
		}
	}
}
