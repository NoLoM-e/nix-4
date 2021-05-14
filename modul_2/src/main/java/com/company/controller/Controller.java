package com.company.controller;

import com.company.tasks.Task1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    private static final Scanner in = new Scanner(System.in);
    private static FileReader fileReader;

    public void run() {

        pointer:
        while (true) {
            System.out.println("Enter number of task to check or 0 to exit");
            switch (in.nextLine()) {
                case "1":
                    try {
                        task1();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    continue pointer;
                case "2":
                    try {
                        task2();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    continue pointer;
                case "3":
                    try {
                        task3();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    continue pointer;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Wrong option");
            }
        }
    }

    private void task1() throws IOException {
        List<String> dates = new ArrayList<>();
        pointer:
        while (true) {
            System.out.println("1 - read dates from file");
            System.out.println("2 - enter dates manually");
            System.out.println("0 - to return");
            switch (in.nextLine()) {
                case "1":
                    fileReader = new FileReader("input1.txt");
                    Scanner s = new Scanner(fileReader);
                    while (s.hasNextLine()){
                        dates.add(s.nextLine());
                    }
                    dates = Task1.task1(dates);
                    dates.forEach(System.out::println);
                    fileReader.close();
                    continue pointer;
                case "2":
                    while (true){
                        System.out.println("Enter date or stop to finish");
                        System.out.println("Date formats dd/mm/yyyy yyyy/mm/dd mm-dd-yyyy");
                        String string = in.nextLine();
                        if(string.equals("stop")){
                            break;
                        }
                        dates.add(string);
                    }
                    dates = Task1.task1(dates);
                    dates.forEach(System.out::println);
                    continue pointer;
                case "0":
                    break pointer;
                default:
                    System.out.println("Wrong option");
            }
        }
    }
    private void task2() throws FileNotFoundException {}
    private void task3() throws FileNotFoundException {}
}
