package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.asset.Assets;
import mitty.bot.Bot;
import mitty.statergy.StatergyManager;
import mitty.statergy.TradeStatergy;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/bot")
public class BotAPI {

	@RequestMapping("status")
	public String tradeDecisions() {
		String status = Assets.instance().toString();
		status += "STATERGY {\n";
		return status + Bot.instance().tradeDecisions() +"}\n";
	}

	
	@RequestMapping("addstatergy/{symbol}/{statergy}")
	public synchronized boolean addStatergy(@PathVariable String symbol, @PathVariable String statergy) {
		
		TradeStatergy statergyinstance= StatergyManager.createStatergy(symbol,statergy);
		
		if(statergyinstance!=null){
			Bot.instance().addStatergy(statergyinstance);
			return true;
		}
		return false;
	}
	
}
