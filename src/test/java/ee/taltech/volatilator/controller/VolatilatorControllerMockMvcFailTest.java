package ee.taltech.volatilator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VolatilatorControllerMockMvcFailTest {

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

    @Test
    void volatilityController_incorrect_date_format_fail() throws Exception {
        mvc.perform(get("/volatilator?startDate=aaaaa").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("fail"))
                .andExpect(jsonPath("$.body.message").value("Failed to parse dates."));
    }

    @Test
    void volatilityController_start_date_after_end_date_fail() throws Exception {
        mvc.perform(get("/volatilator?startDate=2020-01-01&endDate=2019-01-01").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("fail"))
                .andExpect(jsonPath("$.body.message").value("Provided endDate is before startDate."));
    }

    @Test
    void volatilityController_day_range_is_less_than_100_fail() throws Exception {
        mvc.perform(get("/volatilator?startDate=2019-01-01&endDate=2020-01-01").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("fail"))
                .andExpect(jsonPath("$.body.message").value("The date range should be less than 100 days."));
    }

    @Test
    void volatilityController_unhandled_exception_error() throws Exception {
        mvc.perform(get("/volatilator?symbol=aaaa").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("fail"))
                .andExpect(jsonPath("$.body.message").value("API response was null... Most likely the provided symbol does not represent an existing company."));
    }

}
