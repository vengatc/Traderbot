package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.asset.Assets;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/portfolio")
public class PortfolioAPI {

	@RequestMapping("status/")
	public String status() {
		return Assets.instance().getPortfolio().toString();
	}

	@RequestMapping("buy/{symbol}/{number}")
	public synchronized boolean buy(@PathVariable String symbol, @PathVariable int number) {
		return Assets.instance().getPortfolio().buy(symbol, number);
	}

	@RequestMapping("sell/{symbol}/{number}")
	public synchronized boolean sell(@PathVariable String symbol, @PathVariable int number) {
		return Assets.instance().getPortfolio().sell(symbol, number);
	}

	@RequestMapping("sellall/{symbol}")
	public void sellAll(@PathVariable String symbol) {
		Assets.instance().getPortfolio().sellAll(symbol);
	}

}
