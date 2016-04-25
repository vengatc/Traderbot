package mitty.analysis;

import mitty.statergy.TradeStatergy;

public interface Analysis  extends TradeStatergy{

	void chain(Analysis analysis);

	Analysis next();

	

}
