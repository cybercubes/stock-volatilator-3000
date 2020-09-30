package ee.taltech.volatilator.configuration;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeKeyDeserializer extends KeyDeserializer {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) {
        return LocalDateTime.parse(key, DATE_TIME_FORMATTER);
    }

}
