package com.company.controller;

import com.company.entity.User;
import com.company.util.OrderedList;

import java.util.List;
import java.util.Scanner;

public class Controller {

    private final Scanner in = new Scanner(System.in);

    public void run(){
        List<Integer> integers = new OrderedList<>();
        List<User> users = new OrderedList<>();


        while (true) {
            System.out.println("Select object to work with");
            System.out.println("1 - Integer");
            System.out.println("2 - User");
            System.out.println("0 - Exit");

            switch (in.nextLine()) {
                case "1":
                    operations(integers, 0);
                    break;
                case "2":
                    operations(users, 1);
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Wrong index");
            }
        }
    }
    private void operations(List list, int a){
        pointer:
        while (true) {
            System.out.println("Select what you would like to do with the list");
            System.out.println("1 - check size");
            System.out.println("2 - check if list is empty");
            System.out.println("3 - remove by index");
            System.out.println("4 - add");
            System.out.println("5 - see the list's elements");
            System.out.println("6 - get by index");
            System.out.println("7 - create sublist from current list");
            System.out.println("8 - remove all the elements from the list");
            System.out.println("0 - return");

            switch (in.nextLine()) {
                case "1":
                    System.out.println(list.size());
                    break;
                case "2":
                    System.out.println(list.isEmpty());
                    break;
                case "3":
                    System.out.println("Enter index");
                    System.out.println(list.remove(Integer.parseInt(in.nextLine())));
                    break;
                case "4":
                    if(a == 1){
                        User user = createUser();
                        list.add(user);
                    }
                    else {
                        System.out.println("Enter integer");
                        list.add(Integer.parseInt(in.nextLine()));
                    }
                    break;
                case "5":
                    for (Object o : list) {
                        System.out.println(o);
                    }
                    break;
                case "6":
                    System.out.println("Enter index");
                    System.out.println(list.get(Integer.parseInt(in.nextLine())));
                    break;
                case "7":
                    System.out.println("Enter index1");
                    int i = Integer.parseInt(in.nextLine());
                    System.out.println("Enter index2");
                    int i1 = Integer.parseInt(in.nextLine());
                    List list1 = list.subList(i, i1);
                    System.out.println("New list:");
                    for (Object o : list1) {
                        System.out.println(o);
                    }
                    break;
                case "8":
                    System.out.println(list.removeAll(list));
                    break;
                case "0":
                    break pointer;
                default:
                    System.out.println("Incorrect index");
            }
        }
    }

    private User createUser(){
        System.out.println("Enter user's name:");
        String name = in.nextLine();
        System.out.println("Enter user's age:");
        int age = Integer.parseInt(in.nextLine());
        return new User(name, age);
    }
}
