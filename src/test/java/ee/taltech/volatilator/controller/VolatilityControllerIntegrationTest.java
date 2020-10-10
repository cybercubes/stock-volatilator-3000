package ee.taltech.volatilator.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolatilityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getDataDefault() {
        assertEquals(7.904, ((LinkedHashMap) this.restTemplate.getForObject("/volatilator?symbol=FB", LinkedHashMap.class).get("body")).get("volatility"));
    }

}