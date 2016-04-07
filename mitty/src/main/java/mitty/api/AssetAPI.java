package mitty.api;

import org.springframework.web.bind.annotation.RestController;

import mitty.asset.Assets;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class AssetAPI {

    @RequestMapping("/asset/")
    public String totalValue() {
        return Assets.instance().toString();
    }

}
