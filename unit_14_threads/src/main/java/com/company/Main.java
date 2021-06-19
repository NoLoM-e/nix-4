package com.company;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    public static volatile StringBuilder input = new StringBuilder();

    public static void main(String[] args) {

        Thread thread;
        try {
            thread = new Thread(new InputThread(new File("output.txt")));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            input.append(in.nextLine());
            if(StringUtils.endsWith(input, "quit")){
                break;
            }
        }
    }
}
