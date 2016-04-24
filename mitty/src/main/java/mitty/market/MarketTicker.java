package mitty.market;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

// to get the stock price it gets the current price instantaniously.
// refresh every 10 sec.
public class MarketTicker {

	
	static MarketTicker instance = new MarketTicker();
	static ScheduledFuture<?> schedule = null;
	
	public static MarketTicker instance()
	{
		
		if(schedule==null)
		{
			synchronized (MarketTicker.class)
			{
			if(schedule==null){
			instance.start();}
			}
		}
		return instance;
	}
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	Map<String, Double> stocks = new HashMap<String, Double>();
	
	public void addStock(String symbol) {
		// first get the price and then add.
		Double quote = StockQuote.getQuote(symbol);
		assert (quote > 0);
		stocks.put(symbol, quote);
		//System.out.println(symbol + ":" + quote);
	}

	public double getQuote(String symbol)
	{
		Double price= stocks.get(symbol);
		if(price==null)
			addStock(symbol);
		return stocks.get(symbol);
	}
	public void removeStock(String symbol) {
		stocks.remove(symbol);
	}

	void refreshQuotes() {
		for (String stock : stocks.keySet()) {
			addStock(stock);
		}
	}

	public synchronized void start() {
		final Runnable tick = new Runnable() {
			public void run() {
				refreshQuotes();
			}
		};
		schedule = scheduler.scheduleAtFixedRate(tick, 5, 5, SECONDS);

	}

	public synchronized void stop() {
		scheduler.shutdownNow();
	}

	public static void main(String argv[]) {
		MarketTicker ticker = new MarketTicker();
		ticker.addStock("GOOG");
		ticker.addStock("AAPL");
		ticker.addStock("GLD");
		ticker.start();
	}
}
