package mitty.analysis;

import mitty.statergy.TradeStatergyImpl;

abstract public class AnalysisBase extends TradeStatergyImpl implements Analysis{

	public AnalysisBase(String symbol, String name) {
		super(symbol, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isActive() {
		return true;

	}

}
