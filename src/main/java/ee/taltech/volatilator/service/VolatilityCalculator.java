package ee.taltech.volatilator.service;

import ee.taltech.volatilator.models.VolatilityResponse;
import ee.taltech.volatilator.models.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;
import ee.taltech.volatilator.service.alpha.Metadata;
import ee.taltech.volatilator.util.VolatilityUtil;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Objects;
import java.time.LocalDate;
import java.util.Map;
import java.util.EmptyStackException;

@Service
public class VolatilityCalculator {

    public VolatilityResponse transformResponse(DailyResponse dailyResponse) {
        if (dailyResponse == null) {
            return new VolatilityResponse();
        }
        VolatilityResponse volatilityResponse = new VolatilityResponse();
        volatilityResponse.setSymbol(getSymbol(dailyResponse.getMetadata()));
        volatilityResponse.setEntries(getEntries(dailyResponse.getData()));
        volatilityResponse.setVolatility(getVolatility(dailyResponse.getData()));

        return volatilityResponse;
    }

    private String getSymbol(Metadata metadata) {
        return metadata != null ? metadata.getSymbol() : "no symbol";
    }


    private Map<LocalDate, DataPoint> getEntries(Map<LocalDate, DataPoint> dataPoint){
        return dataPoint;
    }


    public static BigDecimal getVolatility(Map<LocalDate, DataPoint> dataPoint){
        //todo Error Checking needs to be leveled up
        if (Objects.isNull(dataPoint) || dataPoint.isEmpty()) {
            throw new EmptyStackException();
        }

        List<BigDecimal> records = new ArrayList<>();
        Set<Map.Entry<LocalDate, DataPoint>> st = dataPoint.entrySet();
        for(Map.Entry<LocalDate, DataPoint> me:st){

            records.add(me.getValue().getAdjusted_close());
        }

        return VolatilityUtil.getDeviation(records);
    }

}
