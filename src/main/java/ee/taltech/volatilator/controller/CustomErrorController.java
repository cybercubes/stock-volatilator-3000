package ee.taltech.volatilator.controller;

import ee.taltech.volatilator.models.FailResponse;
import ee.taltech.volatilator.responses.ErrorResponse;
import ee.taltech.volatilator.responses.Response;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public Response<?> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = Integer.parseInt(status.toString());

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            FailResponse failData = new FailResponse();
            failData.setMessage("Not found.");

            return new ee.taltech.volatilator.responses.FailResponse(failData);
        }

        return new ErrorResponse<>("Unexpected error.");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}