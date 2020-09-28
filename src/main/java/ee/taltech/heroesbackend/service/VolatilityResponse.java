package ee.taltech.heroesbackend.service;

import ee.taltech.heroesbackend.service.alpha.DataPoint;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class VolatilityResponse {

    private String symbol;
    private BigDecimal volatility;
    private Map<LocalDate, DataPoint> entries;
    //private String error;
}
