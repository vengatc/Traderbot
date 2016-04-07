package mitty.bot;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

import mitty.asset.Assets;
import mitty.statergy.TradeStatergy;

//Bot that works to execute the trade statergy.

public class Bot {

	final static Logger logger = Logger.getLogger(Bot.class);

	List<TradeStatergy> statergies = new ArrayList<TradeStatergy>();

	int interval;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	static ScheduledFuture<?> schedule = null;

	public List<TradeStatergy> getStatergies() {
		return statergies;
	}

	public void addStatergy(TradeStatergy statergy) {
		statergies.add(statergy);
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
		schedule = scheduler.scheduleAtFixedRate(tick, 5, 5, SECONDS);

	}

}