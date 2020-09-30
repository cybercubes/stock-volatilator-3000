package ee.taltech.volatilator.service.alpha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class DailyResponse {

    @JsonProperty("Meta Data")
    private Metadata metadata;
    @JsonProperty("Time Series (Daily)")
    private Map<LocalDate, DataPoint> data;
}
