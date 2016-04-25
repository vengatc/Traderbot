package mitty.statergy;

public interface TradeStatergy {

	public void execute();

	public String getDecision();

	public boolean isActive();
	
	public String getName();
	
	public void actedOnDecision(String action);
	
	boolean isHit();
	
	void chain(TradeStatergy analysis);

	TradeStatergy next();

}
