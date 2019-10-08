package com.zum.escape.api.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class DateStringToLocalDateTimeConverter {

    public static LocalDateTime convert(String time) {
        LocalDateTime now = LocalDateTime.now();

        for(String t : time.split(",")) {
            // unknown space to ' '
            t = t.replace(((char)160),' ');
            String[] inputs = t.trim().split("\\s+");

            int num = Integer.parseInt(inputs[0]);
            if(inputs.length < 2) {
                log.error("Fail to parse time : {}", t);
                return now;
            }

            switch(inputs[1].trim()) {
                case "minute":
                case "minutes":
                    now = now.minusMinutes(num);
                    break;
                case "hour":
                case "hours":
                    now = now.minusHours(num);
                    break;

                case "week":
                case "weeks":
                    now = now.minusWeeks(num);
                    break;

                case "day":
                case "days":
                    now = now.minusDays(num);
                    break;

                case "month":
                case "months":
                    now = now.minusMonths(num);
                    break;

                default:
                    log.error("Fail to parse time : {}", t);
            }
        }

        return now;
    }
}
