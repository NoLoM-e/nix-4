package ua.com.alevel.controller;

import ua.com.alevel.entity.CalendarDate;
import ua.com.alevel.service.impl.CalendarImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CalendarController {

    private final CalendarImpl ci = new CalendarImpl();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final String milliseconds = "ms";
    private static final String seconds = "seconds";
    private static final String minutes = "minutes";
    private static final String hours = "hours";
    private static final String days = "days";
    private static final String months = "months";
    private static final String years = "years";
    private static final String centuries = "centuries";


    public void run(String option)  {


        while (true) {
            boolean noErrors = true;
            switch (option) {
                case "0":
                    System.exit(0);
                case "1":
                    do {
                        System.out.println("Choose date format. Date and time must be separated by blank space.");
                        System.out.println("Some examples:");
                        printAllFormats();
                        try {
                            String format = reader.readLine();
                            System.out.println("Enter first date:");
                            CalendarDate date1 = ci.convertToDate(reader.readLine(), format);
                            System.out.println("Enter second date:");
                            CalendarDate date2 = ci.convertToDate(reader.readLine(), format);
                            System.out.println("You want to find difference in:");
                            printAllUnitOptions();
                            String units = reader.readLine();
                            System.out.println("The difference is " +
                                    ci.findDifference(date1, date2, chooseUnits(units)) + " " + chooseUnits(units));
                            noErrors = true;
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                        }
                    } while (!noErrors);

                    break;
                case "2":
                    do {
                        System.out.println("Choose date format. Date and time must be separated by blank space.");
                        System.out.println("Some examples:");
                        printAllFormats();
                        try {
                            String format = reader.readLine();
                            System.out.println("Enter date:");
                            CalendarDate date = ci.convertToDate(reader.readLine(), format);
                            System.out.println("Enter what you would like to add to the date:");
                            printAllUnitOptions();
                            String units = chooseUnits(reader.readLine());
                            System.out.println("How many " + units + " would you like to add? ");
                            CalendarDate result = ci.addToDate(date,Long.parseLong(reader.readLine()), units);
                            System.out.println("Enter output format");
                            String outFormat = reader.readLine();
                            ci.changeFormat(result, outFormat);
                            System.out.print("The result is ");
                            result.print();
                            noErrors = true;
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                            System.out.println("Try again.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                            System.out.println("Try again.");
                        }
                    } while(!noErrors);
                    break;
                case "3":
                    do {
                        System.out.println("Choose date format. Date and time must be separated by blank space.");
                        System.out.println("Some examples:");
                        printAllFormats();
                        try {
                            String format = reader.readLine();
                            System.out.println("Enter date:");
                            CalendarDate date = ci.convertToDate(reader.readLine(), format);
                            System.out.println("Enter what you would like to subtract from the date:");
                            printAllUnitOptions();
                            String units = chooseUnits(reader.readLine());
                            System.out.println("How many " + units + " would you like to subtract? ");
                            CalendarDate result = ci.subtractFromDate(date,Long.parseLong(reader.readLine()), units);
                            System.out.println("Enter output format");
                            String outFormat = reader.readLine();
                            ci.changeFormat(result, outFormat);
                            System.out.print("The result is ");
                            result.print();
                            noErrors = true;
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                            System.out.println("Try again.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                            System.out.println("Try again.");
                        }
                    } while(!noErrors);
                    break;
                case "4":
                    do {

                        try {
                            List<CalendarDate> datesList = new ArrayList<>();
                            System.out.println("Enter how many dates you would like to sort:");
                            int listSize = Integer.parseInt(reader.readLine());
                            for (int i = 0; i < listSize; i++) {
                                System.out.println("Choose date format. Date and time must be separated by blank space.");
                                System.out.println("Some examples:");
                                printAllFormats();
                                String format = reader.readLine();
                                System.out.println("Enter date:");
                                CalendarDate date = ci.convertToDate(reader.readLine(), format);
                                datesList.add(date);
                            }
                            System.out.println("How would you like to sort your dates?");
                            System.out.println("1 - by ascending order");
                            System.out.println("2 - by descending order");
                            String order = reader.readLine();
                            switch(order) {
                                case "1":
                                    datesList = ci.sortAsc(datesList);
                                    break;
                                case "2":
                                    datesList = ci.sortDesc(datesList);
                                    break;
                                default:
                                    throw  new RuntimeException("Wrong option");
                            }
                            System.out.println("Choose output format:");
                            String outFormat = reader.readLine();

                            System.out.println("The result is:");
                            for (CalendarDate d: datesList) {
                                ci.changeFormat(d, outFormat);
                                d.print();
                            }
                            noErrors = true;

                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                            System.out.println("Try again.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            noErrors = false;
                            System.out.println("Try again.");
                        }
                    } while(!noErrors);
                    break;
            }

            System.out.println("Enter 0 to exit or choose another action from menu");
            try {
                option = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void printAllFormats() {
        System.out.println("dd/mm/yy, example: 01/12/21 ");
        System.out.println("m/d/yyyy, example: 3/4/2021 ");
        System.out.println("mmm-d-yy, example: March 4 21 ");
        System.out.println("dd-mmm-yyyy 00:00, example: 09 April 789 45:23 ");

    }

    private void printAllUnitOptions(){
        System.out.println("1 - milliseconds");
        System.out.println("2 - seconds");
        System.out.println("3 - minutes");
        System.out.println("4 - hours");
        System.out.println("5 - days");
        System.out.println("6 - months");
        System.out.println("7 - years");
        System.out.println("8 - centuries");
    }

    private String chooseUnits(String option){
        switch(option) {
            case "1": return milliseconds;
            case "2": return seconds;
            case "3": return minutes;
            case "4": return hours;
            case "5": return days;
            case "6": return months;
            case "7": return years;
            case "8": return centuries;

            default:
                throw new RuntimeException("Wrong units");
        }
    }


}
