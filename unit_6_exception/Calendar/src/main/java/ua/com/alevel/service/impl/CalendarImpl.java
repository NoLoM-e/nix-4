package ua.com.alevel.service.impl;

import ua.com.alevel.entity.CalendarDate;
import ua.com.alevel.service.CalendarService;

import java.util.ArrayList;
import java.util.List;

public class CalendarImpl implements CalendarService {
    private static final long millisecondsADay = 86400000L;
    private static final long millisecondsAnHour = 3600000L;
    private static final long millisecondsAMinute = 60000L;
    private static final long millisecondsASecond = 1000L;
    @Override
    public long toMilliseconds(CalendarDate cd) {
        long total = 0;

        long year = Long.parseLong(cd.getYear());
        for (int i = 0; i < year; i++) {
            if (isLeapYear(i)) {
                total += millisecondsADay * 366;
            } else {
                total += millisecondsADay * 365;
            }
        }


        for (int i = 1; i < cd.getMonth(); i++) {
            total += (millisecondsADay * daysInMonth(i));
        }
        total += (millisecondsADay * cd.getDay());
        total += (cd.getHours() * millisecondsAnHour);
        total += (cd.getMinutes() * millisecondsAMinute);
        total += (cd.getSeconds() * millisecondsASecond);
        total += cd.getMilliseconds();

        return total;
    }


    private static boolean isLeapYear(long year) {
        if (year % 4 == 0) {
            if (year >= 1582) {
                if (year % 100 != 0 ) {
                    return true;
                } else if (year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public CalendarDate convertToDate (String d, String format) {
        CalendarDate cd = new CalendarDate();
        cd.setFormat(format);
        String[] splitedFormat = format.split(" ");
        String[] splitedDate = d.split(" ");
        String [] dates;
        if(splitedFormat.length > 0 && splitedFormat[0].contains("/")) {
            String[] datesFormat = splitedFormat[0].split("/");
            if (!splitedDate[0].contains("/")) {
                cd.setYear(splitedDate[0]);
                cd.setCentury(Integer.parseInt(cd.getYear()) / 100 + 1);
            } else {
                dates = splitedDate[0].split("/");
                cd = convertDate(dates, datesFormat, cd);
            }
            if(splitedFormat.length > 1 && splitedDate.length > 1) {
                String[] time = splitedDate[1].split(":");

                if (!isNotValid(time[0])) {
                    cd.setHours(dateWithoutZero(time[0]));
                }
                if (!isNotValid(time[1])) {
                    cd.setMinutes(dateWithoutZero(time[1]));
                }
                if (time.length > 2) {
                    if (!isNotValid(time[2])) {
                        cd.setSeconds(dateWithoutZero(time[2]));
                    }
                }
                if (time.length > 3) {
                    if (!isNotValid(time[3])) {
                        cd.setMilliseconds(dateWithoutZero(time[3]));
                    }
                }
            }
        }
        else if(splitedFormat.length > 0 && splitedFormat[0].contains("-")) {
            String[] datesFormat = splitedFormat[0].split("-");

            dates = splitedDate;
            if (datesFormat.length > 4 || dates.length > 4) {
                throw new RuntimeException("Wrong format");
            }
            cd = convertDate(dates, datesFormat, cd);
            if (splitedFormat.length == 2 && splitedDate.length == 4) {
                if (splitedFormat[splitedFormat.length - 1].contains(":") && splitedDate[splitedDate.length - 1].contains(":")){
                    String[] time = splitedDate[splitedDate.length - 1].split(":");

                    if (!isNotValid(time[0])) {
                        cd.setHours(dateWithoutZero(time[0]));
                    }
                    if (!isNotValid(time[1])) {
                        cd.setMinutes(dateWithoutZero(time[1]));
                    }
                    if (time.length > 2) {
                        if (!isNotValid(time[2])) {
                            cd.setSeconds(dateWithoutZero(time[2]));
                        }
                    }
                    if (time.length > 3) {
                        if (!isNotValid(time[3])) {
                            cd.setMilliseconds(dateWithoutZero(time[3]));
                        }
                    }
                }
            }
        }



        return cd;
    }

    private CalendarDate convertDate(String[] dates, String[] datesFormat, CalendarDate d) {
        CalendarDate cd = d;
        for (int i = 0; i < dates.length; i++) {
            if(dates[i].contains(":")) {
                return cd;
            }
            if (!isNotValid(datesFormat[i]) ){

                switch (datesFormat[i]) {
                    case "d":
                        if (!isNotValid(dates[i]) ) {
                            cd.setDay(dates[i]);
                        }
                        break;
                    case "dd":
                        if (!isNotValid(dates[i]) ) {
                            cd.setDay(dateWithoutZero(dates[i]));
                        }
                        break;
                    case "m":
                        if (!isNotValid(dates[i]) ) {
                            cd.setMonth(dates[i]);
                        }
                        break;
                    case "mm":
                        if (!isNotValid(dates[i]) ) {
                            cd.setMonth(dateWithoutZero(dates[i]));
                        }
                    case "mmm" :
                        if (!isNotValid(dates[i])) {
                            cd.setMonth(dates[i]);
                        }
                        break;
                    case "yy":
                        if (!isNotValid(dates[i])) {
                            if (dates[i].length() == 2) {
                                cd.setYear("19" + dates[i]);
                            } else {
                                cd.setYear(dates[i]);
                            }
                            cd.setCentury(Integer.parseInt(cd.getYear()) / 100 + 1);
                        }
                        break;
                    case "yyyy":
                        if (!isNotValid(dates[i])) {
                            cd.setYear(dates[i]);
                            cd.setCentury(Integer.parseInt(cd.getYear()) / 100 + 1);
                        }
                }
            }
            else {
                throw new RuntimeException("Wrong format");
            }
        }
        return  cd;
    }

    @Override
    public CalendarDate addToDate(CalendarDate date1, long time, String units) {

        long sum = toMilliseconds(date1) + timeToMs(time, units);
        return toDate(sum);
    }

    @Override
    public CalendarDate subtractFromDate(CalendarDate date1, long time, String units) {
        long diff = toMilliseconds(date1) - timeToMs(time, units);
        return toDate(diff);
    }

    private long timeToMs(long time, String units) {
        switch(units) {
            case "ms":
                break;
            case "seconds":
                time *= millisecondsASecond;
                break;
            case "minutes":
                time *= millisecondsAMinute;
                break;
            case "hours":
                time *= millisecondsAnHour;
                break;
            case "days":
                time *= millisecondsADay;
                break;
            case "months":
                long m = 0;
                for (int i = 0; i < time; i++) {
                    m += (millisecondsADay * daysInMonth(i + 1));
                }
                time = m;
                break;
            case "years":
                long y = 0;
                for (int i = 0; i < time; i++) {
                    if (isLeapYear(i)) {
                        y += millisecondsADay * 366;
                    } else {
                        y += millisecondsADay * 365;
                    }
                }
                time = y;
                break;
            case "centuries":
                long c = 0;
                for (int i = 0; i < time * 100; i++) {
                    if (isLeapYear(i)) {
                        c += millisecondsADay * 366;
                    } else {
                        c += millisecondsADay * 365;
                    }
                }
                time = c;
                break;
            default:
                throw new RuntimeException("no such unit");
        }
        return time;
    }

    @Override
    public long findDifference(CalendarDate date1, CalendarDate date2, String units) {
        long difference;

        if (isBigger(date1, date2)) {
            difference = toMilliseconds(date1) - toMilliseconds(date2);

        } else {
            difference = toMilliseconds(date2) - toMilliseconds(date1);
        }
        switch(units) {
            case "ms":
                return difference;
            case "seconds":
                return msToSeconds(difference);
            case "minutes":
                return msToMinutes(difference);
            case "hours":
                return msToHours(difference);
            case "days":
                return msToDays(difference);
            case "months":
                return msToMonths(difference);
            case "years":
                return msToYears(difference);
            case "centuries":
                return msToYears(difference) / 100;
            default:
                throw new RuntimeException("no such unit");
        }

    }


    @Override
    public List<CalendarDate> sortAsc(List<CalendarDate> dateList) {
        List<CalendarDate> result = new ArrayList<>();

        while (!dateList.isEmpty()) {
            CalendarDate min = dateList.get(0);
            int index = 0;
            for (int j = 0; j < dateList.size(); j++) {
                if(isBigger(min, dateList.get(j))) {
                    min = dateList.get(j);
                    index = j;
                }
            }
            result.add(min);
            dateList.remove(index);
        }
        return result;
    }

    @Override
    public List<CalendarDate> sortDesc(List<CalendarDate> dateList) {
        List<CalendarDate> result = new ArrayList<>();

        while (!dateList.isEmpty()) {
            CalendarDate max = dateList.get(0);
            int index = 0;
            for (int j = 0; j < dateList.size(); j++) {
                if(isBigger(dateList.get(j), max)) {
                    max = dateList.get(j);
                    index = j;
                }
            }
            result.add(max);
            dateList.remove(index);
        }
        return result;
    }

    private static long daysInMonth(int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            return 31L;
        } else if (month == 2) {
            return 28L;
        } else {
            return 30L;
        }

    }

    @Override
    public void changeFormat(CalendarDate date, String format) {
        date.setFormat(format);
    }

    private static boolean isNotValid(String data) {
        return "".equals(data) || data.equals(" ");
    }


    @Override
    public boolean isBigger(CalendarDate date1, CalendarDate date2) {
        long d1 = toMilliseconds(date1);
        long d2 = toMilliseconds(date2);
        return (d1 > d2);
    }
    private static String dateWithoutZero(String d) {
        if (d.charAt(0) == '0' && d.length() == 2){
            return Character.toString(d.charAt(1));
        }
        return d;
    }

    private long msToSeconds(long ms) {
        return ms / millisecondsASecond;
    }

    private long msToMinutes(long ms) {
        return ms / millisecondsAMinute;
    }

    private long msToHours(long ms) {
        return ms / millisecondsAnHour;
    }

    private long msToDays(long ms) {
        return ms / millisecondsADay;
    }

    private long msToMonths(long ms) {
        int months = 1;
        while (ms - millisecondsADay * daysInMonth(months) > 0) {
            ms -= (millisecondsADay * daysInMonth(months));
            months++;
        }
        return (long) months - 1;
    }

    private long msToYears(long ms) {
        int year = 0;
        while (ms - millisecondsADay * 366 > 0) {
            if (isLeapYear(year)) {
                ms -= millisecondsADay * 366;
            } else {
                ms -= millisecondsADay * 365;
            }
            year++;
        }
        return year;
    }


    private static CalendarDate toDate(long ms) {
        CalendarDate cd = new CalendarDate();
        int century = 0;
        int year = 0;
        while (ms - millisecondsADay * 366 > 0) {
            if (isLeapYear(year)) {
                ms -= millisecondsADay * 366;
            } else {
                ms -= millisecondsADay * 365;
            }
            year++;
        }
        cd.setCentury(year / 100 + 1);

        cd.setYear(Integer.toString(year%100));
        int months = 1;
        while (ms - millisecondsADay * daysInMonth(months) > 0) {
            ms -= (millisecondsADay * daysInMonth(months));
            months++;
        }

        cd.setMonth(Integer.toString(months));
        long days = ms/millisecondsADay;
        cd.setDay(Long.toString(days));
        ms %= (millisecondsADay * days);

        long hours = ms / millisecondsAnHour;
        cd.setHours(Long.toString(hours));
        if (hours != 0) {
            ms %= (millisecondsAnHour * hours);
        }


        long minutes = ms / millisecondsAMinute;
        cd.setMinutes(Long.toString(minutes));
        if (minutes != 0) {
            ms %= (millisecondsAMinute * minutes);
        }

        long seconds = ms / millisecondsASecond;
        cd.setSeconds(Long.toString(seconds));
        if (seconds != 0) {
            ms %= (millisecondsASecond * seconds);
        }


        cd.setMilliseconds(Long.toString(ms));
        return cd;
    }
}
