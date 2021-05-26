package com.company;

import com.company.entity.User;
import com.company.util.CSVParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();

        List<User> users = csvParser.readFrom("src/main/resources/data.csv", User.class);

        for (User u : users){
            System.out.println(u);
        }
    }
}
