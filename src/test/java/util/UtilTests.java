package util;

import ee.taltech.volatilator.util.VolatilityUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTests {
    @Test
    void test(){
        List<BigDecimal> arr = new ArrayList<>();
        arr.add(BigDecimal.ONE);
        arr.add(BigDecimal.ONE);
        arr.add(BigDecimal.ONE);
        assertEquals(BigDecimal.ZERO, VolatilityUtil.getDeviation(arr));
    }
}
