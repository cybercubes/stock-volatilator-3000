package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.service.AlphaVantage;
import ee.taltech.volatilator.models.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;
import ee.taltech.volatilator.service.alpha.Metadata;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VolatilatorControllerMockMvcTest2 {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlphaVantage alphaVantage;

    @Test
    void financeController_returns_custom_AAPL_response() throws Exception {
        DailyResponse response = new DailyResponse();
        response.setMetadata(metadata());
        response.setData(data());
        Mockito.when(alphaVantage.queryForDaily(Mockito.anyString())).thenReturn(response);
        mvc.perform(get("/volatilator").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.body.symbol").value("AAPL"))
                .andExpect(jsonPath("$.body.volatility").value(BigDecimal.valueOf(7.483)));
    }

    private Map<LocalDate, DataPoint> data() {
        DataPoint v1 = new DataPoint();
        v1.setAdjusted_close(BigDecimal.valueOf(13));
        DataPoint v2 = new DataPoint();
        v2.setAdjusted_close(BigDecimal.valueOf(25));
        DataPoint v3 = new DataPoint();
        v3.setAdjusted_close(BigDecimal.valueOf(7));

        return Map.of(LocalDate.of(2020, 9, 11), v1,
                LocalDate.of(2020, 9, 12), v2,
                LocalDate.of(2020, 9, 13), v3);
    }

    private Metadata metadata() {
        Metadata metadata = new Metadata();
        metadata.setSymbol("AAPL");
        return metadata;
    }

}
