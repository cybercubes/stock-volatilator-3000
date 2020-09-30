package ee.taltech.volatilator.util;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Service
public class VolatilityUtil {
    public static BigDecimal getDeviation(List<BigDecimal> records){
        BigDecimal average = records.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(records.size()), RoundingMode.HALF_EVEN);

        return records.stream()
                .map(x -> x.subtract(average).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(average, RoundingMode.HALF_UP)
                .sqrt(new MathContext(4));
    }
}
