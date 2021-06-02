package com.company;

import com.company.data.Data;
import com.company.entity.User;
import com.company.util.CSVMapper;
import com.company.util.CSVParser;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        CSVMapper csvMapper = new CSVMapper();

        //String url = "data.csv";
        Path url = null;
        try {
             url = new Main().getPath();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {

            Data data = csvParser.parse(url.toString());

            System.out.println(Arrays.toString(data.getNames()));

            List<User> users = csvMapper.map(data, User.class);

            for (User u : users){
                System.out.println(u);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Path getPath() throws URISyntaxException {
        return Paths.get(getClass().getResource("/data.csv").toURI());
    }
}
