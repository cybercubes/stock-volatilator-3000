package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.service.AlphaVantage;
import ee.taltech.volatilator.models.VolatilityResponse;
import ee.taltech.volatilator.models.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;
import ee.taltech.volatilator.service.alpha.Metadata;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VolatilatorControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void volatilityController_returns_default_response() throws Exception {
        mvc.perform(get("/volatilator").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.body.symbol").value("IBM"))
                .andExpect(jsonPath("$.body.volatility").exists());
    }
}
