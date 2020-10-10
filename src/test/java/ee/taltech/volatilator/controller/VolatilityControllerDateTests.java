package ee.taltech.volatilator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.LinkedHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolatilityControllerDateTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void dateErrorTest() {
        String url = "/volatilator?endDate=2040-10-03&startDate=2090-09-06&symbol=IBM";
        assertThat(((LinkedHashMap) this.restTemplate.getForObject(url, LinkedHashMap.class).get("body")).get("message")).isEqualTo("Provided endDate cannot be in the future.");
    }

    @Test
    void dateParsingErrorTest() {
        String url = "/volatilator?endDate=2040-10-03&startDate=2090709-06&symbol=IBM";
        assertThat(((LinkedHashMap) this.restTemplate.getForObject(url, LinkedHashMap.class).get("body")).get("message")).isEqualTo("Failed to parse dates.");
    }
}