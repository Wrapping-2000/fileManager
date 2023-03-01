package sample.model;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 用于获取本地时间的工具类
 * @classname
 * @date 2022.6.21 21:04$
 */
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @description 用于获取本地时间并转化为特定格式的工具类
 * @paramaeter
 * @return
 * @author  Wrapping
 * @date  2022-07-19
 * @throws
 */
public class DateUtil {

    /** 特定时间格式 */
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /** 特定时间格式 */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * @description 由本地时间转换为特定格式时间
     * @paramaeter  [date]
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * @description 完成String向LocalDate的方法
     * @paramaeter  [dateString]
     * @return  java.time.LocalDate
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * @description 完成LocalDate向String转换的方法
     * @paramaeter  [localDate]
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public static String localDateToString(LocalDate localDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateStr = localDate.format(fmt);
        return localDateStr;
    }

    /**
     * @description 完成Date向LocalDate的方法
     * @paramaeter  [date]
     * @return  java.time.LocalDate
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();//atZone()方法返回在指定时区从此Instant生成的ZonedDateTime
        return localDate;
    }

    /**
     * @description 不合法的日期判断方法
     * @paramaeter  [dateString]
     * @return  boolean
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
}

