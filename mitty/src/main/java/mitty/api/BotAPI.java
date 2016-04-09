package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.bot.Bot;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/bot")
public class BotAPI {

	@RequestMapping("status")
	public String tradeDecisions() {
		return Bot.instance().tradeDecisions();
	}

}
