package com.company;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    public static StringBuffer input = new StringBuffer();

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Thread thread;
        try {
            thread = new Thread(new InputThread(new File("target/output.txt")));
            thread.start();
        } catch (IOException e) {
            logger.error("Unable to create thread", e);
            throw new RuntimeException(e);
        }

        while (true){
            input.append(in.nextLine());
            if(StringUtils.endsWith(input, "quit")){
                break;
            }
        }
    }
}
