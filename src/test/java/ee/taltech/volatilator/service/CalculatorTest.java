package ee.taltech.volatilator.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import ee.taltech.volatilator.service.alpha.DataPoint;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Map;


public class CalculatorTest {

    @Test
    void CalculatorNullTest(){
       assertThrows(EmptyStackException.class, () -> VolatilityCalculator.getVolatility(null));
    }

    @Test
    void CalculatorEmptyTest(){
        assertThrows(EmptyStackException.class, () -> VolatilityCalculator.getVolatility(Collections.emptyMap()));
    }
}
