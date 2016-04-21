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

	  System.out.println ("Assets at start");
	  System.out.println ( Assets.instance().toString());
	  System.out.println("-------------------------------");
	  if(Assets.instance().getTotalValue()==0){    
	  System.out.println("Doing seed funding");	  
	  //seed portfolio once;	  
      Assets.instance().getMoneyMarket().deposit(10000);
      String stock = "TSLA";
      double price = MarketTicker.instance().getQuote(stock);
      int number = (int) (5000/price);
      Assets.instance().getPortfolio().buy(stock, number);

      stock = "GLD";
      price = MarketTicker.instance().getQuote(stock);
      number = (int) (5000/price);
      Assets.instance().getPortfolio().buy(stock, number);
      
      
	 
	  stock = "TSLA";
      UpHill uphillstatergy = new UpHill(1, stock,0);
      bot.addStatergy(uphillstatergy);
      ProfitBooker profitbooker = new ProfitBooker(1, stock);
      bot.addStatergy(profitbooker);

      stock = "GLD";
      uphillstatergy = new UpHill(1, stock,0);
      bot.addStatergy(uphillstatergy);
	  }
	  
      
      bot.start();
      
	}
}
