package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.asset.Assets;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/assets")
public class AssetAPI {

    @RequestMapping("status/")
    public String totalValue() {
        return Assets.instance().toString();
    }

}
