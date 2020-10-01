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
        arr.add(BigDecimal.valueOf(3));
        arr.add(BigDecimal.valueOf(9));
        arr.add(BigDecimal.valueOf(21));
        assertEquals(BigDecimal.valueOf(7.483), VolatilityUtil.getDeviation(arr));
    }

    @Test
    void nullTest(){
        List<BigDecimal> arr = new ArrayList<>();
        arr.add(BigDecimal.valueOf(3));
        arr.add(BigDecimal.valueOf(9));
        arr.add(BigDecimal.valueOf(21));
        arr.add(null);
        assertEquals(BigDecimal.valueOf(7.483), VolatilityUtil.getDeviation(arr));
    }

    //todo assertions on getAverage
}
