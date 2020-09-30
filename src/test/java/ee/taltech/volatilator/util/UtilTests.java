package ee.taltech.volatilator.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTests {
    //todo more assertions on getDeviation
    @Test
    void test(){
        List<BigDecimal> arr = new ArrayList<>();
        arr.add(BigDecimal.ONE);
        arr.add(BigDecimal.ONE);
        arr.add(BigDecimal.ONE);
        assertEquals(BigDecimal.ZERO, VolatilityUtil.getDeviation(arr));
    }
    //todo assertions on getAverage
}
