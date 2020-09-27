package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.service.alpha.DailyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinanceService {

    @Autowired
    private AlphaVantage alphaVantage;
    @Autowired
    private FinanceCalculator financeCalculator;

    public FinanceResponse queryForData(String symbol) {
        DailyResponse dailyResponse = alphaVantage.queryForDaily(symbol);
        return financeCalculator.transformResponse(dailyResponse);
    }
}
