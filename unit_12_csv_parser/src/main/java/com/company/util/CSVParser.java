package com.company.util;


import com.company.data.Data;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser {

    public Data parse(String url) throws IOException, CsvException {
        try(CSVReader csvReader = new CSVReader(new FileReader(url))){

            return new Data(csvReader.readNext(), csvReader.readAll());

        } catch (IOException | CsvException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
