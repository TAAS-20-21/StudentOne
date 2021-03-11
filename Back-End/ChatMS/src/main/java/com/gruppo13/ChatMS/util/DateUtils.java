package com.gruppo13.ChatMS.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public final class DateUtils {

    public static final String TIMEZONE = "CET";

    public static final String PATTERN_DATE = "dd/MM/yyyy";
    public static final String PATTERN_DATEHOUR = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERN_DATE_REVERSE = "yyyy-MM-dd";

    public static final DateFormat FORMATTER_DATE = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
    public static final DateFormat FORMATTER_DATEHOUR = new SimpleDateFormat(PATTERN_DATEHOUR, Locale.ITALY);
    public static final DateFormat FORMATTER_DATE_REVERSE = new SimpleDateFormat(PATTERN_DATE_REVERSE, Locale.ITALY);

    public static final Time START_DATE = getCurrentDate(DateUtils.FORMATTER_DATE_REVERSE);
    public static final Time END_DATE = new Time("4712-12-31", FORMATTER_DATE_REVERSE);



    private DateUtils() {
    }

    public static Time getTime(Timestamp s) {
        return new Time(s.getTime(), FORMATTER_DATEHOUR);
    }

    public static Time getTime(Timestamp s, DateFormat formatter) {
        return new Time(s.getTime(), formatter);
    }

    public static Time getCurrentTime() {
        return new Time(FORMATTER_DATEHOUR);
    }

    public static Time getCurrentDate(DateFormat formatter) {
        return new Time(formatter);
    }

    /**
     * Classe di utilità che fornisce il time in formato Date e String.
     */
    public static final class Time {
        private final TimeZone zone = TimeZone.getTimeZone(TIMEZONE);
        private final Calendar cal = GregorianCalendar.getInstance(zone);
        private final DateFormat formatter;


        private Time(DateFormat formatter) {
            this.formatter = formatter;
        }

        private Time(long millis, DateFormat formatter) {
            this(formatter);
            if(millis > 0) {
                cal.setTimeInMillis(millis);
            }
        }

        private Time(String source, DateFormat formatter) {
            this(formatter);
            try {
                cal.setTime(formatter.parse(source));
            } catch (Throwable t) {
                throw new RuntimeException(t.getMessage(), t);
            }
        }

        /**
         * Ritorna la data contenuta in formato Date
         */
        public java.util.Date toDate() {
            return cal.getTime();
        }

        /**
         * Ritorna oggetto Calendar
         */
        public Calendar getCal() {
            return cal;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return formatter.format(cal.getTime());
        }
    }
}
