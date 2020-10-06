package ee.taltech.volatilator.util;

import ee.taltech.volatilator.service.alpha.DailyResponse;
import ee.taltech.volatilator.service.alpha.DataPoint;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    //todo: write tests for this
    public static DailyResponse filterDailyResponse(DailyResponse response, LocalDate startDate, LocalDate endDate) {
        DailyResponse result = response;
        Map<LocalDate, DataPoint> newEntries = new HashMap<>();

        for (Map.Entry<LocalDate,DataPoint> entry : response.getData().entrySet()) {
            if (entry.getKey().isAfter(startDate) && entry.getKey().isBefore(endDate)) {
                newEntries.put(entry.getKey(), entry.getValue());
            }
        }

        result.setData(newEntries);

        return result;
    }
}
