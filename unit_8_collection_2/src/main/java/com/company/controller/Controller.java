package com.company.controller;

import com.company.util.MathSet;

import java.util.Scanner;

public class Controller {

    private final Scanner in = new Scanner(System.in);

    public void run(){
        System.out.println("Enter initial number of elements");
        int size = Integer.parseInt(in.nextLine());
        Double[] arr = new Double[size];

        for (int i = 0; i < size; i++) {
            System.out.printf("Enter element %d \n", i);
            arr[i] = Double.parseDouble(in.nextLine());
        }
        MathSet<Double> set = new MathSet<>(arr);

        while (true){
            System.out.println("Select action:");
            System.out.println("1 - add element");
            System.out.println("2 - join with another MathSet");
            System.out.println("3 - sort in ascending order");
            System.out.println("4 - sort in descending order");
            System.out.println("5 - get element by index");
            System.out.println("6 - get max element");
            System.out.println("7 - get min element");
            System.out.println("8 - get average");
            System.out.println("9 - get median");
            System.out.println("10 - squash");
            System.out.println("11 - clear set");
            System.out.println("12 - delete elements");
            System.out.println("13 - print set");
            System.out.println("0 - exit");

            switch (in.nextLine()){
                case "1":
                    System.out.println("Enter element");
                    set.add(Double.parseDouble(in.nextLine()));
                    break;
                case "2":
                    set.join(createSet());
                    break;
                case "3":
                    set.sortAsc();
                    break;
                case "4":
                    set.sortDesc();
                    break;
                case "5":
                    System.out.println("Enter index");
                    System.out.println(set.get(Integer.parseInt(in.nextLine())));
                    break;
                case "6":
                    System.out.println(set.getMax());
                    break;
                case "7":
                    System.out.println(set.getMin());
                    break;
                case "8":
                    System.out.println(set.getAverage());
                    break;
                case "9":
                    System.out.println(set.getMedian());
                    break;
                case "10":
                    System.out.println("Enter index1");
                    int i = Integer.parseInt(in.nextLine());
                    System.out.println("Enter index2");
                    int i1 = Integer.parseInt(in.nextLine());
                    System.out.println(set.squash(i, i1));
                    break;
                case "11":
                    set.clear();
                    break;
                case "12":
                    set.clear(createSet().toArray());
                    break;
                case "13":
                    System.out.println(set);
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Incorrect option");
            }
        }
    }
    private MathSet createSet(){
        System.out.println("Enter number of elements");
        int size = Integer.parseInt(in.nextLine());
        Double[] arr = new Double[size];

        for (int i = 0; i < size; i++) {
            System.out.printf("Enter element %d \n", i);
            arr[i] = Double.parseDouble(in.nextLine());
        }
        MathSet<Double> set = new MathSet<>(arr);

        return set;
    }
}
