package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.service.VolatilityResponse;
import ee.taltech.volatilator.service.VolatilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/volatilator")
@RestController
public class VolatilityController {
    @Autowired
    private VolatilityService volatilityService;

    @GetMapping()
    public VolatilityResponse getData(@RequestParam(defaultValue = "IBM") String symbol){
        return volatilityService.queryForData(symbol);
    }
}
