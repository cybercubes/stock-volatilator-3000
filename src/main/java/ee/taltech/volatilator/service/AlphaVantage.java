package ee.taltech.volatilator.service;

import ee.taltech.volatilator.configuration.AlphaVantageConfig;
import ee.taltech.volatilator.service.alpha.DailyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Service
public class AlphaVantage {

    public static final String DAILY = "TIME_SERIES_DAILY_ADJUSTED";
    @Autowired
    private AlphaVantageConfig config;
    @Autowired
    private RestTemplate restTemplate;

    public DailyResponse queryForDaily(final String symbol){
        String url = config.getUrl()
                + "?function=" + DAILY
                + "&symbol=" + symbol
                + "&apikey=" + config.getKey();
        ResponseEntity<DailyResponse> entity = restTemplate.getForEntity(url, DailyResponse.class);
        //todo do some error handling in the future

        if (isNull(entity.getBody().getMetadata())) {
            throw new RuntimeException("Received empty JSON response");
        }

        return entity.getBody();
    }
}
