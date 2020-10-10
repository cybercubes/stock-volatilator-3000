package ee.taltech.volatilator.util;

import ee.taltech.volatilator.models.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    //todo: write tests for this
    public static void filterDailyResponse(DailyResponse response, String startDateString, String endDateString)
            throws DateTimeParseException, IllegalArgumentException {
        Map<LocalDate, DataPoint> newEntries = new HashMap<>();

        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Provided endDate cannot be in the future.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Provided endDate is before startDate.");
        }

        if (ChronoUnit.DAYS.between(startDate, endDate) > 99) {
            throw new IllegalArgumentException("The date range should be less than 100 days.");
        }

        if (response.getData() == null) {
            System.out.println(response);
            throw new IllegalArgumentException("API response was null... Most likely the provided symbol does not represent an existing company.");
        }

        for (Map.Entry<LocalDate,DataPoint> entry : response.getData().entrySet()) {
            if (entry.getKey().isAfter(startDate) && entry.getKey().isBefore(endDate)) {
                newEntries.put(entry.getKey(), entry.getValue());
            }
        }

        response.setData(newEntries);
    }
}
