package ee.taltech.volatilator.service;

import ee.taltech.volatilator.models.VolatilityResponse;
import ee.taltech.volatilator.models.DailyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;

import static ee.taltech.volatilator.util.ResponseUtil.filterDailyResponse;

@Service
public class VolatilityService {

    @Autowired
    private AlphaVantage alphaVantage;
    @Autowired
    private VolatilityCalculator volatilityCalculator;

    public VolatilityResponse queryForData(String symbol, String startDate, String endDate)
            throws DateTimeParseException, IllegalArgumentException {

        DailyResponse dailyData = alphaVantage.queryForDaily(symbol);

        filterDailyResponse(dailyData, startDate, endDate);
        return volatilityCalculator.transformResponse(dailyData);
    }
}