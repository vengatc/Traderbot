package mitty.bot;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

import mitty.analysis.Analysis;
import mitty.asset.Assets;
import mitty.statergy.TradeStatergy;

//Bot that works to execute the trade statergy.

public class Bot {

	private static Bot instance = new Bot();

	static public Bot instance() {
		return instance;
	}

	final static Logger logger = Logger.getLogger(Bot.class);

	Set<TradeStatergy> statergies = new LinkedHashSet<TradeStatergy>();
	Set<TradeStatergy> analysis = new LinkedHashSet<TradeStatergy>();

	int interval;
	private final ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
	private final ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(1);

	static ScheduledFuture<?> statergySchedule = null;
	static ScheduledFuture<?> analysisSchedule = null;

	public Set<TradeStatergy> getStatergies() {
		return statergies;
	}

	public Set<TradeStatergy> getAnalaysis() {
		return analysis;
	}

	public String tradeDecisions() {
		String decisions = "";
		for (TradeStatergy statergy : getStatergies()) {

			if (statergy.isActive()) {
				decisions += statergy.getDecision() + "\n";
			}

		}
		return decisions;
	}

	public String analysisDecisions() {
		String decisions = "";
		for (TradeStatergy statergy : getAnalaysis()) {

			if (statergy.isActive()) {
				decisions += statergy.getDecision() + "\n";
			}

		}
		return decisions;
	}

	public void addStatergy(TradeStatergy statergy) {
		if (statergy instanceof Analysis) {
			addAnalysis(statergy);
		} else {
			statergies.add(statergy);

		}
	}

	public void addAnalysis(TradeStatergy statergy) {
		analysis.add(statergy);
	}

	public synchronized void start() {
		final Runnable tick = new Runnable() {
			public void run() {
				for (TradeStatergy statergy : statergies) {
					statergy.execute();
				}

				System.out.println(Assets.instance().toString());

			}
		};
		statergySchedule = scheduler1.scheduleAtFixedRate(tick, 5, 5, SECONDS);

		final Runnable analysistick = new Runnable() {
			public void run() {
				for (TradeStatergy statergy : analysis) {
					statergy.execute();
				}

				System.out.println(Assets.instance().toString());

			}
		};
		analysisSchedule = scheduler2.scheduleAtFixedRate(analysistick, 5, 5, SECONDS);

	}

}