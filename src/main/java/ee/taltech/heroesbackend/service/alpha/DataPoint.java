package ee.taltech.heroesbackend.service.alpha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataPoint {

    @JsonProperty("1. open")
    private BigDecimal open;
    @JsonProperty("2. high")
    private BigDecimal high;
    @JsonProperty("3. low")
    private BigDecimal low;
    @JsonProperty("4. close")
    private BigDecimal close;
    @JsonProperty("5. volume")
    private BigDecimal volume;
}
