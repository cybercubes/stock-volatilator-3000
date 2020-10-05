package ee.taltech.volatilator.service;

import ee.taltech.volatilator.dto.VolatilityResponse;
import ee.taltech.volatilator.service.alpha.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;
import ee.taltech.volatilator.service.alpha.Metadata;
import ee.taltech.volatilator.util.VolatilityUtil;
import org.springframework.stereotype.Service;
//Arch's imports
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.time.LocalDate;
import java.util.Map;

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
        /*Optional<Map.Entry<LocalDate, DataPoint>> lastEntryOp = getLastEntry(dailyResponse.getData());
        if (lastEntryOp.isPresent()) {
            Map.Entry<LocalDate, DataPoint> maxEntry = lastEntryOp.get();
            financeResponse.setDate(maxEntry.getKey());
            financeResponse.setPrice(maxEntry.getValue().getClose());
        }*/
        return volatilityResponse;
    }

    private String getSymbol(Metadata metadata) {
        return metadata != null ? metadata.getSymbol() : "no symbol";
    }

    private Map<LocalDate, DataPoint> getEntries(Map<LocalDate, DataPoint> dataPoint){
        return dataPoint;
    }

    private BigDecimal getVolatility(Map<LocalDate, DataPoint> dataPoint/*, LocalDate start, LocalDate end*/){
        // Error Checking needs to be leveled up
        if (dataPoint == null || dataPoint.isEmpty()) {
            return null;
        }
        /*else if(start == null || end == null){
            //return BigDecimal.ZERO; Actually calculate full stuff
            end = LocalDate.now();
            start = LocalDate.of(2000, end.getMonth(), end.getDayOfMonth());
        }
        else if(start.compareTo(end) <= 0){
            return BigDecimal.ZERO;
        }*/

        List<BigDecimal> records = new ArrayList<>();
        Set<Map.Entry<LocalDate, DataPoint>> st = dataPoint.entrySet();
        for(Map.Entry<LocalDate, DataPoint> me:st){
            /*if(start.compareTo(me.getKey()) >= 0){
                records.add(me.getValue().getAdjusted_close());
            }
            if(end.compareTo(me.getKey()) == 0){
                break;
            }*/
            records.add(me.getValue().getAdjusted_close());
        }

        return VolatilityUtil.getDeviation(records);
    }

    /*private Optional<Map.Entry<LocalDate, DataPoint>> getLastEntry(Map<LocalDate, DataPoint> data) {
        return data.entrySet().stream().max(Map.Entry.comparingByKey());
    }*/
}
