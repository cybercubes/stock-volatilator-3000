package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.service.alpha.DailyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolatilityService {

    @Autowired
    private AlphaVantage alphaVantage;
    @Autowired
    private VolatilityCalculator volatilityCalculator;

    public VolatilityResponse queryForData(String symbol) {
        DailyResponse dailyResponse = alphaVantage.queryForDaily(symbol);
        return volatilityCalculator.transformResponse(dailyResponse);
    }
}
