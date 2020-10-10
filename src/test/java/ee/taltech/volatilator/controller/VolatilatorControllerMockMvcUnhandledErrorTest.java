package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.service.VolatilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class VolatilatorControllerMockMvcUnhandledErrorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VolatilityService mockVolService;

    @Test
    void volatilityController_unhandled_error() throws Exception {
        when(mockVolService.queryForData(anyString(), anyString(), anyString())).thenThrow(new Exception());

        mvc.perform(get("/volatilator").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("error"));

        verify(mockVolService, times(1)).queryForData(anyString(), anyString(), anyString());
    }
}
