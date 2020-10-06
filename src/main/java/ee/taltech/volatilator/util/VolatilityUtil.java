package ee.taltech.volatilator.util;

import ee.taltech.volatilator.service.alpha.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Objects.isNull;

@Service
public class VolatilityUtil {

    private static List<BigDecimal> filterNulls(List<BigDecimal> records){
        return records.stream().filter(x->!isNull(x)).collect(Collectors.toList());
    }
    public static BigDecimal getDeviation(List<BigDecimal> records){
        records = filterNulls(records);
        List<BigDecimal> finalRecords = records;
        return records.stream()
                .map(x -> x.subtract(getAverage(finalRecords)).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(records.size()), RoundingMode.HALF_UP)
                .sqrt(new MathContext(4));
    }

    public static BigDecimal getAverage(List<BigDecimal> records){
        records = filterNulls(records);
        return records.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(records.size()), RoundingMode.HALF_EVEN);
    }

    //todo: write tests for this
    public static DailyResponse filterDailyResponse(DailyResponse response, LocalDate startDate, LocalDate endDate) {
        DailyResponse result = response;
        Map<LocalDate, DataPoint> newEntries = new HashMap<>();

        for (Map.Entry<LocalDate,DataPoint> entry : response.getData().entrySet()) {
            if (entry.getKey().isAfter(startDate) && entry.getKey().isBefore(endDate)) {
                newEntries.put(entry.getKey(), entry.getValue());
            }
        }

        result.setData(newEntries);

        return result;
    }
}
