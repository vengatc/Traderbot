package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.asset.Assets;
import mitty.bot.Bot;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/bot")
public class BotAPI {

	@RequestMapping("status")
	public String tradeDecisions() {
		String status = Assets.instance().getPortfolio().toString();
		status += "STATERGY\n";
		return status + Bot.instance().tradeDecisions();
	}

}
