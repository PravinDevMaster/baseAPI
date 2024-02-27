package Base.APITemplete.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    private static final String DateFormat = "yyyy-MM-dd HH:mm:ss";

    public static Date getCurrentSqlDate() {
        Long currentTimeMillis = System.currentTimeMillis();
        return new Date(currentTimeMillis);
    }

    public static Date parseStringToSqlDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        java.util.Date utilDate = sdf.parse(dateString);
        return new Date(utilDate.getTime());
    }

    public static String formatSqlDateToString(Date sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        return sdf.format(sqlDate);
    }
}
