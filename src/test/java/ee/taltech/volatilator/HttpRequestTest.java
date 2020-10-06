package ee.taltech.volatilator;

import ee.taltech.volatilator.models.VolatilityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() {
        assertThat(this.restTemplate.getForObject("/", String.class)).isEqualTo("API is up");
    }

    @Test
    public void NonExistingMappingShouldReturnNotFoundMessage() {
        assertThat(this.restTemplate.getForObject( "/nonExistingMapping",
                String.class)).contains("error-404");
    }

    @Test
    public void CorrectMappingReturnsJson() {
        VolatilityResponse forObject = this.restTemplate.getForObject("/volatilator",
                VolatilityResponse.class);



        //assertThat(forObject).contains("error-404");
    }

}
