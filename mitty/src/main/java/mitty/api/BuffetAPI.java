package mitty.api;

import mitty.buffet.buybusiness.Business;
import mitty.buffet.buybusiness.BusinessFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buffet")
public class BuffetAPI {

    @RequestMapping("value/{symbol}")
    public String getValue(@PathVariable String symbol) {
        Business business = BusinessFactory.me.apply(symbol);
        return business.getBusinessValue();
    }


}
