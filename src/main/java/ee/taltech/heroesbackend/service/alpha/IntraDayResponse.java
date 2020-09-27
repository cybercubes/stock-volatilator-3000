package ee.taltech.heroesbackend.service.alpha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ee.taltech.heroesbackend.configuration.LocalDateTimeKeyDeserializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class IntraDayResponse {

    @JsonProperty("Meta Data")
    private Metadata metadata;
    @JsonProperty("Time Series FX (5min)")
    @JsonDeserialize(keyUsing = LocalDateTimeKeyDeserializer.class)
    private Map<LocalDateTime, DataPoint> data;
}
