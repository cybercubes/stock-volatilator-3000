package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.models.FailResponse;
import ee.taltech.volatilator.responses.ErrorResponse;
import ee.taltech.volatilator.responses.Response;
import ee.taltech.volatilator.models.VolatilityResponse;
import ee.taltech.volatilator.responses.SuccessResponse;
import ee.taltech.volatilator.service.VolatilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.EmptyStackException;

@RequestMapping({"/volatilator", "/volatilator2"})
@RestController
@Slf4j
@Component
public class VolatilityController {
    @Autowired
    private VolatilityService volatilityService;
    @GetMapping()
    @Cacheable(value = "responses", key = "#symbol.concat(#startDate).concat(#endDate)")
    public Response<?> getData(@RequestParam(defaultValue = "IBM") String symbol,
                                            @RequestParam(defaultValue = "2020-09-06")String startDate,
                                            @RequestParam(defaultValue = "2020-10-03")String endDate ){
        VolatilityResponse volatilityResponse;
        try {
            volatilityResponse = volatilityService.queryForData(symbol, startDate, endDate);
        } catch (DateTimeParseException e) {
            FailResponse failData = new FailResponse();
            failData.setMessage("Failed to parse dates.");
            return new ee.taltech.volatilator.responses.FailResponse(failData);
        } catch (EmptyStackException e) {
            FailResponse failData = new FailResponse();
            failData.setMessage("No data entries found.");
            return new ee.taltech.volatilator.responses.FailResponse(failData);
        } catch (IllegalArgumentException e) {
            FailResponse failData = new FailResponse();
            failData.setMessage(e.getMessage());
            return new ee.taltech.volatilator.responses.FailResponse(failData);
        } catch (Exception e) {
            return new ErrorResponse<>("Unexpected error. " + e.getClass() + "; " + e.getMessage());
        }

        log.info("symbol = {}, startDate = {}, endDate = {}",
                symbol, startDate, endDate);
        log.info("response = {}", volatilityResponse);
        return new SuccessResponse<>(volatilityResponse);
    }
}
