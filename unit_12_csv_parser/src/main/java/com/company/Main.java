package com.company;

import com.company.data.Data;
import com.company.entity.User;
import com.company.util.CSVMapper;
import com.company.util.CSVParser;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        CSVMapper csvMapper = new CSVMapper();

        String url = "src/main/resources/data.csv";

        Data data = csvParser.parse(url);

        System.out.println(Arrays.toString(data.getNames()));

        List<User> users = csvMapper.map(data, User.class);

        for (User u : users){
            System.out.println(u);
        }
    }
}
