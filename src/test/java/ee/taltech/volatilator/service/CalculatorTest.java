package ee.taltech.volatilator.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.EmptyStackException;


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
