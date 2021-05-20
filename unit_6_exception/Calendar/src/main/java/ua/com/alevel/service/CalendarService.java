package ua.com.alevel.service;

import ua.com.alevel.entity.CalendarDate;

import java.util.List;

public interface CalendarService {
    CalendarDate convertToDate(String date, String format);
    long findDifference(CalendarDate date1, CalendarDate date2, String units);
    CalendarDate addToDate(CalendarDate date1, long time, String units);
    CalendarDate subtractFromDate(CalendarDate date1, long time, String units);
    long toMilliseconds(CalendarDate cd);
    void changeFormat(CalendarDate date, String format);
    boolean isBigger(CalendarDate date1, CalendarDate date2);
    List<CalendarDate> sortAsc(List<CalendarDate> dateList);
    List<CalendarDate> sortDesc(List<CalendarDate> dateList);

}
