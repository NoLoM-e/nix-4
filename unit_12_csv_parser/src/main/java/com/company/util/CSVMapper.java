package com.company.util;

import com.company.anot.ColumnName;
import com.company.data.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVMapper {

    public <T> List<T> map(Data data, Class<T> c) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        List<T> result = new ArrayList<>();

        try {

            for (int i = 0; i < data.rowCount(); i++) {
                Constructor<T> constructor = c.getConstructor();
                T instance = constructor.newInstance();
                List<Field> fields = Arrays.stream(instance.getClass().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(ColumnName.class))
                        .collect(Collectors.toList());

                for (Field field : fields) {
                    String name = field.getDeclaredAnnotation(ColumnName.class).value();


                    Class<?> type = field.getType();

                    if (int.class == type || Integer.class == type) {
                        field.set(instance, Integer.parseInt(data.get(i, name)));
                    } else if (long.class == type || Long.class == type) {
                        field.set(instance, Long.parseLong(data.get(i, name)));
                    } else if (float.class == type || Float.class == type) {
                        field.set(instance, Float.parseFloat(data.get(i, name)));
                    } else if (double.class == type || Double.class == type) {
                        field.set(instance, Double.parseDouble(data.get(i, name)));
                    } else if (boolean.class == type || Boolean.class == type) {
                        field.set(instance, Boolean.parseBoolean(data.get(i, name)));
                    } else if (String.class == type) {
                        field.set(instance, data.get(i, name));
                    } else {
                        throw new RuntimeException("no type found");
                    }
                }

                result.add(instance);
            }
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
