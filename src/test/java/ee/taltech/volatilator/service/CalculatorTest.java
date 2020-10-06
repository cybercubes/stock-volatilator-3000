package ee.taltech.volatilator.service;

import ee.taltech.volatilator.util.VolatilityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorTest {

    @Test
    void CalculatorNullTest(){
        Assertions.assertThrows(EmptyStackException.class, () -> );
    }

}
