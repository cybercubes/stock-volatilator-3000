package ee.taltech.volatilator.service.alpha;

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
    @JsonProperty("5. adjusted close")
    private BigDecimal adjusted_close;
    @JsonProperty("6. volume")
    private BigDecimal volume;
    @JsonProperty("7. dividend amount")
    private BigDecimal dividend_amount;
    @JsonProperty("8. split coefficient")
    private BigDecimal split_coefficient;
}
