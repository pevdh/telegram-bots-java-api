package co.vandenham.telegram.bots.gmtbot;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeZones {


    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a (EEEE)");
    private final static Pattern timeZoneRegex = Pattern.compile("(GMT)([+-])([0-9]{1,2})");

    public static String getCurrentTime(TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        dateFormat.setTimeZone(timeZone);
        return "(" + timeZone.getID() + ") " + dateFormat.format(calendar.getTime());
    }

    public static boolean isValidTimeZone(String text) {
        text = text.toUpperCase();
        Matcher matcher = timeZoneRegex.matcher(text);
        if (!matcher.matches()) {
            return false;
        }

        int timeZoneModifier = Integer.valueOf(matcher.group(3));
        return timeZoneModifier >= -12 && timeZoneModifier <= 12;
    }

    public static class TimeZoneComparator implements Comparator<TimeZone> {
        public final static TimeZoneComparator INSTANCE = new TimeZoneComparator();

        private TimeZoneComparator() {}

        @Override
        public int compare(TimeZone t1, TimeZone t2) {
            return t1.getRawOffset() - t2.getRawOffset();
        }
    }
}
