package ee.taltech.volatilator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomErrorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void NonExistingMappingShouldReturnNotFoundMessage() {
        assertThat(this.restTemplate.getForObject( "/nonExistingMapping",
                String.class)).contains("{\"status\":\"fail\",\"body\":{\"message\":\"Not found.\"}}");
    }

}
