package mitty.analysis;

import mitty.statergy.TradeStatergy;

public interface Analysis  extends TradeStatergy{

	boolean isHit();

	void chain(Analysis analysis);

	Analysis next();

	void process();

}
