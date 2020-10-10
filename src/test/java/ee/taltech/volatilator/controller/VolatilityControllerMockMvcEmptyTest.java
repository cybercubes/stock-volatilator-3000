package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.service.AlphaVantage;
import ee.taltech.volatilator.models.DailyResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VolatilityControllerMockMvcEmptyTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlphaVantage alphaVantage;

    @Test
    void volatilityController_returns_custom_NPC_response() throws Exception {
        DailyResponse response = new DailyResponse();
        Mockito.when(alphaVantage.queryForDaily(Mockito.anyString())).thenReturn(response);
        mvc.perform(get("/volatilator?symbol=Empty").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("fail"));
    }

}
