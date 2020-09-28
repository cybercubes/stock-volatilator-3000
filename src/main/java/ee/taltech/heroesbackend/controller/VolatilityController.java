package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.service.VolatilityResponse;
import ee.taltech.heroesbackend.service.VolatilityService;
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
