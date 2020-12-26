package ee.taltech.volatilator.util;

import ee.taltech.volatilator.service.VolatilityCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTests {
    @Test
    void deviationTest(){
        assertEquals(BigDecimal.valueOf(7.483),
                VolatilityUtil.getDeviation(Arrays.asList(
                        BigDecimal.valueOf(3),
                        BigDecimal.valueOf(9),
                        BigDecimal.valueOf(21))));
    }

    @Test
    void deviationTest2(){
        assertEquals(BigDecimal.valueOf(7.483),
                VolatilityUtil.getDeviation(Arrays.asList(
                        BigDecimal.valueOf(13),
                        BigDecimal.valueOf(25),
                        BigDecimal.valueOf(7))));
    }

    @Test
    void deviationTestNullTest(){
        assertEquals(BigDecimal.valueOf(7.483),
                VolatilityUtil.getDeviation(Arrays.asList(
                        BigDecimal.valueOf(3),
                        BigDecimal.valueOf(9),
                        BigDecimal.valueOf(21),
                        null)));
    }

    @Test
    void deviationTestNullsTest(){
        assertThrows(ArithmeticException.class, () -> VolatilityUtil.getDeviation(
                Arrays.asList(
                        null,
                        null,
                        null,
                        null)));
    }

    @Test
    void averageTest(){
        assertEquals(BigDecimal.valueOf(11), VolatilityUtil.getAverage(Arrays.asList(
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(9),
                BigDecimal.valueOf(21))));
    }

    @Test
    void averageTestRounding(){
        assertEquals(BigDecimal.valueOf(10), VolatilityUtil.getAverage(Arrays.asList(
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(8),
                BigDecimal.valueOf(7),
                BigDecimal.valueOf(20))));
    }

    @Test
    void averageNullTest(){
        assertEquals(BigDecimal.valueOf(11), VolatilityUtil.getAverage(
                Arrays.asList(
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(9),
                BigDecimal.valueOf(21),
                null)));
    }

    @Test
    void averageNullsTest(){
        assertThrows(ArithmeticException.class, () -> VolatilityUtil.getAverage(
                Arrays.asList(
                        null,
                        null,
                        null,
                        null)));
    }
}
