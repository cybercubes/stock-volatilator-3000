package ee.taltech.heroesbackend.service;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VolatilityResponse {

    private String symbol;
    private LocalDate date;
    private BigDecimal price;
}
