package ee.taltech.volatilator.service.alpha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Metadata {

    @JsonProperty("1. Information")
    private String information;
    @JsonProperty("2. Symbol")
    private String symbol;
    @JsonProperty("3. Last Refreshed")
    private String refreshed;
    @JsonProperty("4. Output Size")
    private String outputSize;
    @JsonProperty("5. Time Zone")
    private String timeZone;
}
