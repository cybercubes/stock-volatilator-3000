package ee.taltech.volatilator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.taltech.volatilator.service.alpha.DataPoint;
import ee.taltech.volatilator.service.alpha.Metadata;
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
