package com.company.util;

import com.company.anot.ColumnName;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVParser {

    public <T> List<T> readFrom(String url, Class<T> c){

        List<T> result = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(url))) {

            List<String> names = Arrays.asList(reader.readNext());

            List<String[]> strings = reader.readAll();

            for (String[] s : strings){
                Constructor<T> constructor = c.getConstructor();
                T instance = constructor.newInstance();
                List<Field> fields = Arrays.stream(instance.getClass().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(ColumnName.class))
                        .collect(Collectors.toList());

                for (Field field : fields){
                    String name = field.getDeclaredAnnotation(ColumnName.class).value();

                    int i = names.indexOf(name);

                    Class<?> type = field.getType();

                    if (int.class == type || Integer.class == type) {
                        field.set(instance, Integer.parseInt(s[i]));
                    } else if (long.class == type || Long.class == type) {
                        field.set(instance, Long.parseLong(s[i]));
                    } else if (float.class == type || Float.class == type) {
                        field.set(instance, Float.parseFloat(s[i]));
                    } else if (double.class == type || Double.class == type) {
                        field.set(instance, Double.parseDouble(s[i]));
                    } else if (boolean.class == type || Boolean.class == type) {
                        field.set(instance, Boolean.parseBoolean(s[i]));
                    } else if (String.class == type) {
                        field.set(instance, s[i]);
                    } else {
                        throw new RuntimeException("no type found");
                    }
                }

                result.add(instance);
            }

        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | CsvException e) {
            e.printStackTrace();
        }

        return result;
    }

}
