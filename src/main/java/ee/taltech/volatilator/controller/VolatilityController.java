package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.dto.VolatilityResponse;
import ee.taltech.volatilator.service.VolatilityService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/volatilator")
@RestController
public class VolatilityController {
    @Autowired
    private VolatilityService volatilityService;
    @GetMapping()
    public VolatilityResponse getData(@RequestParam(defaultValue = "IBM") String symbol,
                                      @RequestParam(defaultValue = "2020-09-06")String startDate,
                                      @RequestParam(defaultValue = "2020-10-03")String endDate ){

        return volatilityService.queryForData(symbol, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
