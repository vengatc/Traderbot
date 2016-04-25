package mitty.analysis;

import mitty.statergy.TradeStatergyImpl;

abstract public class AnalysisBase extends TradeStatergyImpl implements Analysis {

	protected Analysis next;



	public AnalysisBase(String symbol, String name) {
		super(symbol, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isActive() {
		return true;

	}

	

	public Analysis next() {
		return next;
	}


	@Override
	public void chain(Analysis analysis) {

		if (next != null) {
			next.chain(analysis);
		} else {
			next = analysis;
		}

	}

	@Override
	public void execute() {
		this.process();
		if (isHit()) {
			
			if (next() != null) {
				next.execute();
			}
			actedOnDecision(decision);

		}
		

	}
}
