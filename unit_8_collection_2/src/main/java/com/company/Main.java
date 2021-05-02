package com.company;

import com.company.controller.Controller;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();

        Set<Number> set = new HashSet<>(null);

        set.remove(1);
    }
}
