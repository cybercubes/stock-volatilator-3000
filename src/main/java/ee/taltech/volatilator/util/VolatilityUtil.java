package ee.taltech.volatilator.util;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
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
}
