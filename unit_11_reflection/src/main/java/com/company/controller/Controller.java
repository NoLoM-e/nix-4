package com.company.controller;

import com.company.anot.PropertyKey;
import com.company.prop.AppProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    AppProperties appProperties = new AppProperties();

    public void run(){

        try {

            Properties properties = loadProperties();


            Field[] declaredFields = appProperties.getClass().getDeclaredFields();

            List<Field> fields = Arrays.stream(declaredFields)
                    .filter(field -> field.isAnnotationPresent(PropertyKey.class))
                    .collect(Collectors.toList());


            for (Field field : fields){
                String s = properties.getProperty(field.getDeclaredAnnotation(PropertyKey.class).value());
                Class<?> type = field.getType();

                if (int.class == type || Integer.class == type) {
                    field.set(appProperties, Integer.parseInt(s));
                } else if (long.class == type || Long.class == type) {
                    field.set(appProperties, Long.parseLong(s));
                } else if (float.class == type || Float.class == type) {
                    field.set(appProperties, Float.parseFloat(s));
                } else if (double.class == type || Double.class == type) {
                    field.set(appProperties, Double.parseDouble(s));
                } else if (boolean.class == type || Boolean.class == type) {
                    field.set(appProperties, Boolean.parseBoolean(s));
                } else if (String.class == type) {
                    field.set(appProperties, s);
                } else {
                    throw new RuntimeException("no type found");
                }


            }
            for (Field field : fields){
                System.out.println(field.get(appProperties));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() {

        Properties props = new Properties();

        try(InputStream input = Controller.class.getResourceAsStream("/app.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }
}
