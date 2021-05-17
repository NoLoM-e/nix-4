package ua.com.alevel.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CalendarDate {

    private int century;
    private String year;
    private int month;
    private int day;
    private int hours;
    private int minutes;
    private int seconds;
    private long milliseconds;
    private String format;

    private static final Map<Integer, String> months = new HashMap<>();

    public CalendarDate(){
        year = "2021";
        century = 21;
        month = 1;
        day = 1;
        hours = 0;
        minutes = 0;
        seconds = 0;
        milliseconds = 0;
        format = "dd/mm/yy";
        if (months.isEmpty()) {
            fillMap();
        }
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public void setCentury(int century) {


        if (century < 0) {
            throw new RuntimeException("Wrong century format");
        }

        this.century = century;
    }



    public void setYear(String year){
        for (int i = 0; i < year.length(); i++) {
            if (i == 0 && year.length() > 2 && year.charAt(i) == '0') {
                throw new RuntimeException("Wrong year format");
            }
            if (!Character.isDigit(year.charAt(i))) {
                throw new RuntimeException("Wrong year format");
            }
        }
        this.year = year;
    }

    public void setMonth(String month) {
        if (month == null) {
            throw new RuntimeException("value is null");
        }
        for (Map.Entry<Integer, String> item : months.entrySet()) {
            if (item.getValue().equalsIgnoreCase(month)) {
                this.month = item.getKey();
                return;
            }

        }

        for (int i = 0; i < month.length(); i++) {
            if (!Character.isDigit(month.charAt(i))) {
                throw new RuntimeException("Wrong month format");
            }
        }
        int m = Integer.parseInt(month);
        if (m > 12 || m < 0) {
            throw new RuntimeException("Wrong month format");
        }
        this.month = m;
    }

    public void setDay(String day) {
        for (int i = 0; i < day.length(); i++) {
            if (!Character.isDigit(day.charAt(i))) {
                throw new RuntimeException("Wrong day format");
            }
        }

        int d = Integer.parseInt(day);
        if (d < 1) {
            throw new RuntimeException("Wrong day format");
        }

        if (this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7
                || this.month == 8 || this.month == 10 || this.month == 12) {
            if (d > 31) {
                throw new RuntimeException("Wrong day format");
            }
        } else if (this.month == 2) {
            if (d > 29) {
                throw new RuntimeException("Wrong day format");
            }
        } else {
            if (d > 30) {
                throw new RuntimeException("Wrong day format");
            }
        }

        this.day = d;
    }

    public void setHours(String hours) {
        for (int i = 0; i < hours.length(); i++) {
            if (!Character.isDigit(hours.charAt(i))) {
                throw new RuntimeException("Wrong hours format");
            }
        }
        int h = Integer.parseInt(hours);
        if (h < 0 || h >= 24) {
            throw new RuntimeException("Wrong hours format");
        }
        this.hours = h;
    }

    public void setMinutes(String minutes) {
        for (int i = 0; i < minutes.length(); i++) {
            if (!Character.isDigit(minutes.charAt(i))) {
                throw new RuntimeException("Wrong minutes format");
            }
        }
        int m = Integer.parseInt(minutes);
        if (m < 0 || m >= 60) {
            throw new RuntimeException("Wrong minutes format");
        }
        this.minutes = m;
    }

    public void setSeconds(String seconds) {
        for (int i = 0; i < seconds.length(); i++) {
            if (!Character.isDigit(seconds.charAt(i))) {
                throw new RuntimeException("Wrong seconds format");
            }
        }
        int s = Integer.parseInt(seconds);
        if (s < 0 || s >= 60) {
            throw new RuntimeException("Wrong seconds format");
        }
        this.seconds = s;
    }
    public void setMilliseconds(String milliseconds) {
        for (int i = 0; i < milliseconds.length(); i++) {
            if (!Character.isDigit(milliseconds.charAt(i))) {
                throw new RuntimeException("Wrong milliseconds format");
            }
        }
        int ms = Integer.parseInt(milliseconds);
        if (ms < 0 || ms >= 1000) {
            throw new RuntimeException("Wrong milliseconds format");
        }
        this.milliseconds = ms;
    }

    public void print(){
        String[] splitedFormat = this.format.split(" ");
        String[] dates;
        String del;
        if (splitedFormat[0].contains("/")) {
            dates = splitedFormat[0].split("/");
            del = "/";
        } else {
            dates = splitedFormat[0].split("-");
            del = " ";
        }
        for (int i = 0; i < dates.length; i++) {
            if(dates[i].contains(":")) {
                break;
            }
            switch(dates[i]) {
                case "d":
                    System.out.print(this.day);
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                case "dd":
                    if (this.day < 10) {
                        System.out.print("0" + this.day);
                    } else {
                        System.out.print(this.day);
                    }
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                case "m":
                    System.out.print(this.month);
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                case "mm":
                    if (this.month < 10) {
                        System.out.print("0" + this.month);
                    } else {
                        System.out.print(this.month);
                    }
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                case "mmm":
                    System.out.print(months.get(this.month));
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                case "yy":
                    printYY();
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                case "yyyy":
                    System.out.print(Integer.toString(this.century - 1));
                    printYY();
                    printDelIfNeeded(dates.length, i, del);
                    continue;
                default:
                    throw new RuntimeException("Wrong format");
            }
        }
        System.out.print(" ");
            if (splitedFormat.length > 1 && splitedFormat[splitedFormat.length - 1].contains(":")) {
                String[] time = splitedFormat[splitedFormat.length - 1].split(":");
                if(time.length > 0) {
                    if (this.hours < 10) {
                        System.out.print("0" + this.hours);
                    } else {
                        System.out.print(this.hours);
                    }
                }
                if (time.length > 1) {
                    if (this.minutes < 10) {
                        System.out.print(":0" + this.minutes);
                    } else {
                        System.out.print(":" + this.minutes);
                    }
                }
                if (time.length > 2) {
                    if (this.seconds < 10) {
                        System.out.print(":0" + this.seconds);
                    } else {
                        System.out.print(":" + this.seconds);
                    }
                }
                if (time.length > 3){
                    if (this.milliseconds < 10) {
                        System.out.print(":0" + this.milliseconds);
                    } else {
                        System.out.print(":" + this.milliseconds);
                    }
                }
            }

        System.out.println();

    }


    private void printYY(){
        if (this.year.length() >= 3) {
            System.out.print( "" + this.year.charAt(this.year.length() - 2)
                    + this.year.charAt(this.year.length() - 1));
        } else if (this.year.length() == 1) {
            System.out.print("0" + this.year);
        } else {
            System.out.print(this.year);
        }
    }

    private void printDelIfNeeded(int size, int i, String del) {
        if (i != size - 1) {
            System.out.print(del);
        }
    }




    private void fillMap(){
        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");
        months.put(4, "April");
        months.put(5, "May");
        months.put(6, "June");
        months.put(7, "July");
        months.put(8, "August");
        months.put(9, "September");
        months.put(10, "October");
        months.put(11, "November");
        months.put(12, "December");
    }
}
