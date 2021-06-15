package com.company.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    public void parse(List<String[]> list){

        double income = 0;
        double consumption = 0;
        double delta = 0;

        try (FileWriter csv = new FileWriter("account.csv")){

            logger.info("Started parsing");

            csv.append(String.join(",", list.get(0)));
            csv.append("\n");
            for (int i = 1; i < list.size(); i++) {
                String[] s = list.get(i);
                if(Double.parseDouble(s[2]) > 0){
                    income += Double.parseDouble(s[2]);
                }
                else {
                    consumption += Double.parseDouble(s[2]);
                }
                csv.append(String.join(",", Arrays.asList(s)));
                csv.append("\n");
            }
            csv.append(String.join(",", Arrays.asList(new String[]{"income", "consumption", "delta", ""})));
            csv.append("\n");
            delta = income + consumption;
            csv.append(String.valueOf(income)).append(",").append(String.valueOf(consumption)).append(",").append(String.valueOf(delta)).append(",").append("").append("\n");

            logger.info("Finished parsing");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
