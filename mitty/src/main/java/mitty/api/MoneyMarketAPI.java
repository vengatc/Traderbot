package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.asset.Assets;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/moneymarket")
public class MoneyMarketAPI {

    @RequestMapping("status")
    public String status() {
        return Assets.instance().getMoneyMarket().toString();
    }

    @RequestMapping("deposit/{amount}")
	public synchronized void deposit(@PathVariable double amount) {
    	Assets.instance().getMoneyMarket().deposit(amount);
    }
	
    @RequestMapping("take/{amount}")
	public synchronized double take(@PathVariable double amount) {
    	return Assets.instance().getMoneyMarket().take(amount);
    }
	
	
}
