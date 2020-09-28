package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.service.alpha.DailyResponse;
import ee.taltech.heroesbackend.service.alpha.DataPoint;
import ee.taltech.heroesbackend.service.alpha.Metadata;
import org.springframework.stereotype.Service;
//Arch's imports
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

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
            return BigDecimal.ZERO;
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

        BigDecimal average = records.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(records.size()), RoundingMode.HALF_EVEN);

        BigDecimal volatility = records.stream()
                .map(x -> x.subtract(average).pow(2))
                .collect(Collectors.toList())
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(average, RoundingMode.HALF_UP)
                .sqrt(new MathContext(4));

        return volatility;
    }

    /*private Optional<Map.Entry<LocalDate, DataPoint>> getLastEntry(Map<LocalDate, DataPoint> data) {
        return data.entrySet().stream().max(Map.Entry.comparingByKey());
    }*/
}
