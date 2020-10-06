package ee.taltech.volatilator.service;

import ee.taltech.volatilator.configuration.AlphaVantageConfig;
import ee.taltech.volatilator.models.DailyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
                + "&outputsize=compact"
                + "&apikey=" + config.getKey();
        ResponseEntity<DailyResponse> entity = restTemplate.getForEntity(url, DailyResponse.class);
        //todo do some error handling in the future

        /*
        if (isNull(Objects.requireNonNull(entity.getBody()).getMetadata())) {
            throw new RuntimeException("Received empty JSON response");
        }*/

        return entity.getBody();
    }
}
