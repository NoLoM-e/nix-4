package ua.com.alevel.view;

import ua.com.alevel.controller.CalendarController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalendarView {

    public void run() {
        CalendarController cc = new CalendarController();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hello! Please select what you would like to do.");
        System.out.println("1 - find difference between dates");
        System.out.println("2 - add something to date");
        System.out.println("3 - subtract something from date");
        System.out.println("4 - sort dates");
        System.out.println("0 - exit");


        try {
            String option = reader.readLine();
            cc.run(option);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
