package com.company.controller;

import com.company.tasks.Task1;
import com.company.tasks.Task2;
import com.company.tasks.Task3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    private static final Scanner in = new Scanner(System.in);

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
                        //task2();
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    continue pointer;
                case "3":
                    try {
                        task3();
                    } catch (Exception e){
                        e.printStackTrace();
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
        FileReader fileReader;

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
    private void task3() throws IOException {
        FileReader fileReader = new FileReader("input.txt");
        Scanner s = new Scanner(fileReader);

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter("output.txt"));

        int size = Integer.parseInt(s.nextLine());
        List<String> cities = new ArrayList<>();

        int[][] arr = new int[size][size];

        for (int i = 0; i < size; i++){
            cities.add(s.nextLine());
            int temp = Integer.parseInt(s.nextLine());
            for (int j = 0; j < temp; j++){
                arr[i][Integer.parseInt(s.next()) - 1] = Integer.parseInt(s.nextLine().replace(" ", ""));
            }
        }

        int temp = Integer.parseInt(s.nextLine());
        for (int i = 0; i < temp; i++){
            fileWriter.write(String.valueOf(Task3.task3(arr, cities.indexOf(s.next()), cities.indexOf(s.nextLine().replace(" ", "")))));
            fileWriter.write("\n");
        }
        fileReader.close();
        FileReader fileReader1 = new FileReader("output.txt");
        Scanner s1 = new Scanner(fileReader1);
        while (s1.hasNextLine()){
            System.out.println(s1.nextLine());
        }
        fileReader.close();
        fileWriter.close();
    }
}
