package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.analysis.RangeLowPriceHit;
import mitty.asset.Assets;
import mitty.bot.Bot;
import mitty.market.MarketTicker;
import mitty.statergy.ProfitBooker;
import mitty.statergy.StatergyManager;
import mitty.statergy.TradeStatergy;
import mitty.statergy.UpHill;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/bot")
public class BotAPI {

	@RequestMapping("status")
	public String status() {
		String status = Assets.instance().toString();
		status += "STATERGY {\n";
		status += Bot.instance().tradeDecisions() + "}\n";
		
		status += "ANALYSIS {\n";
		return status + Bot.instance().analysisDecisions() + "}\n";
	}

	// setting the statergy for the symbol.
	@RequestMapping("addstatergy/{symbol}/{statergy}")
	public boolean addStatergy(@PathVariable String symbol, @PathVariable String statergy) {

		TradeStatergy statergyinstance = StatergyManager.createStatergy(symbol, statergy);

		if (statergyinstance != null) {
			Bot.instance().addStatergy(statergyinstance);
			return true;
		}
		return false;
	}
	
	


	@RequestMapping("loadtestdata")
	public void loadSampleData() {
		Bot bot = Bot.instance();
		if (Assets.instance().getTotalValue() == 0) {
			System.out.println("Doing seed funding");
			// seed portfolio once;
			Assets.instance().getMoneyMarket().deposit(10000);
			String stock = "TSLA";
			double price = MarketTicker.instance().getQuote(stock);
			int number = (int) (5000 / price);
			Assets.instance().getPortfolio().buy(stock, number);

			stock = "GLD";
			price = MarketTicker.instance().getQuote(stock);
			number = (int) (5000 / price);
			Assets.instance().getPortfolio().buy(stock, number);

			stock = "TSLA";
			UpHill uphillstatergy = new UpHill(1, stock, 0);
			bot.addStatergy(uphillstatergy);
			ProfitBooker profitbooker = new ProfitBooker(1, stock);
			bot.addStatergy(profitbooker);

			stock = "GLD";
			uphillstatergy = new UpHill(1, stock, 0);
			bot.addStatergy(uphillstatergy);
			
			RangeLowPriceHit rlstager = new RangeLowPriceHit(stock);
			bot.addStatergy(rlstager);
		}

	}

}
