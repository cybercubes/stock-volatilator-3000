package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.models.FailResponse;
import ee.taltech.volatilator.responses.ErrorResponse;
import ee.taltech.volatilator.responses.Response;
import ee.taltech.volatilator.models.VolatilityResponse;
import ee.taltech.volatilator.responses.SuccessResponse;
import ee.taltech.volatilator.service.VolatilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.EmptyStackException;

@RequestMapping("/volatilator")
@RestController
public class VolatilityController {
    @Autowired
    private VolatilityService volatilityService;
    @GetMapping()
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

        return new SuccessResponse<>(volatilityResponse);
    }
}
