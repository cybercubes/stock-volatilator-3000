package ee.taltech.volatilator.service;

import ee.taltech.volatilator.dto.VolatilityResponse;
import ee.taltech.volatilator.service.alpha.DailyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static ee.taltech.volatilator.util.VolatilityUtil.filterDailyResponse;

@Service
public class VolatilityService {

    @Autowired
    private AlphaVantage alphaVantage;
    @Autowired
    private VolatilityCalculator volatilityCalculator;

    public VolatilityResponse queryForData(String symbol, LocalDate startDate, LocalDate endDate) {
        DailyResponse dailyResponse = alphaVantage.queryForDaily(symbol);
        dailyResponse = filterDailyResponse(dailyResponse, startDate, endDate);
        return volatilityCalculator.transformResponse(dailyResponse);
    }
}
