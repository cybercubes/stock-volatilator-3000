package ee.taltech.volatilator.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolatilityControllerDefIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getDataDefault() {
        assertEquals(1.652, ((LinkedHashMap) this.restTemplate.getForObject("/volatilator", LinkedHashMap.class).get("body")).get("volatility"));
    }

}