package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public interface convert {
    static String toPeriod(LocalDateTime l) {
        return l.getHour() <= 11 ? "AM" : "PM";
    }

    static int to24Hour(int hour, String period) {
        if (hour < 1 || hour > 12)
            return -1;
        switch(period) {
            case "AM": return hour < 12 ? hour : 0;
            case "PM": return hour < 12 ? hour+12 : 12;
            default: return -1;
        }
    }

    /**
     * Parse string to convert to LocalDateTime object
     * @param str - the DateTime string
     *  patterns:
     *          yyyy-MM-dd HH:mm
     *          yyyy-MM-dd HH:mm:ss
     *          yyyy-MM-dd HH:mm:ss.f   <---- Timestamp toTimestampFormat in the database
     * @return - the DateTime as an object
     */
    static LocalDateTime toLocalDateTime(String str) {
        if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"))
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        else if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"))
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        else if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d*"))
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"));
        else {
            return null;
        }
    }

    static String toTimestampFormat(LocalDateTime l) {
        return l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+":00.0";
    }
}
