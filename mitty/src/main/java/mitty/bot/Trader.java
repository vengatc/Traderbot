package mitty.bot;

import mitty.asset.Assets;
import mitty.market.MarketTicker;
import mitty.statergy.ProfitBooker;
import mitty.statergy.StatergyManager;
import mitty.statergy.TradeStatergy;
import mitty.statergy.UpHill;

public class Trader {
	public static void start() {
		Bot bot = Bot.instance();
		for (TradeStatergy statergy : StatergyManager.getAllStatergy()) {
			bot.addStatergy(statergy);
		}

		System.out.println("Assets at start");
		System.out.println(Assets.instance().toString());
		System.out.println("-------------------------------");
		bot.start();

	}
}
