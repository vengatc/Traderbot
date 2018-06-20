package mitty.api;

import mitty.buffet.buybusiness.Business;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    @RequestMapping("/")
    public String getWatchListValue() {
        return Business.getWatchlistValue();
    }
}
