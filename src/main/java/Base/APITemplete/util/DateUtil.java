package Base.APITemplete.util;

import java.sql.Date;

public class DateUtil {

    public static Date getCurrentSqlDate() {
        Long currentTimeMillis = System.currentTimeMillis();
        return new Date(currentTimeMillis);
    }
}