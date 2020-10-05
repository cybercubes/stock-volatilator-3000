package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.dto.VolatilityResponse;
import ee.taltech.volatilator.service.VolatilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RequestMapping("/volatilator")
@RestController
public class VolatilityController {
    @Autowired
    private VolatilityService volatilityService;
    @GetMapping()
    public VolatilityResponse getData(@RequestParam(defaultValue = "IBM") String symbol,
                                      @RequestParam(defaultValue = "2018-09-09")Date startDate,
                                      @RequestParam(defaultValue = "2017-09-09")Date endDate ){

        return volatilityService.queryForData(symbol);
    }
}
