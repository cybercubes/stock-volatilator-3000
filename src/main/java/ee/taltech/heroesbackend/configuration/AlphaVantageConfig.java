package ee.taltech.heroesbackend.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.alpha-vantage")
public class AlphaVantageConfig {

    private String url;
    private String key;

}
