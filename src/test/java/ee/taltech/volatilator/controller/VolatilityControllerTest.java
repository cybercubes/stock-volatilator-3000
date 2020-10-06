package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.dto.VolatilityData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolatilatorControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void volatilator_returns_response_data_from_default(){
        ResponseEntity<VolatilityData> entity = testRestTemplate.getForEntity("/volatilator", VolatilityData.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    void volatilator_returns_non_null_response_from_default() {
        ResponseEntity<VolatilityData> entity = testRestTemplate.getForEntity("/volatilator", VolatilityData.class);
        VolatilityData volatilityResponse = entity.getBody();
        assertNotNull(volatilityResponse);
        assertEquals("IBM", volatilityResponse.getSymbol());
        assertNotNull(volatilityResponse.getEntries());
        assertNotNull(volatilityResponse.getVolatility());
    }
}
