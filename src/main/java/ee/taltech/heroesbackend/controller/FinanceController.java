package ee.taltech.heroesbackend.controller;

import ee.taltech.heroesbackend.service.FinanceResponse;
import ee.taltech.heroesbackend.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/finance")
@RestController
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @GetMapping()
    public FinanceResponse getData(@RequestParam(defaultValue = "IBM") String symbol){
        return financeService.queryForData(symbol);
    }

}
