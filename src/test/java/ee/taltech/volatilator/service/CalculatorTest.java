package ee.taltech.volatilator.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static ee.taltech.volatilator.service.VolatilityCalculator.*;

import java.util.EmptyStackException;

import ee.taltech.volatilator.service.VolatilityCalculator.*;

public class CalculatorTest {

    @Test
    void CalculatorNullTest(){
       assertThrows(EmptyStackException.class, () -> VolatilityCalculator.getVolatility(null));
    }

}
