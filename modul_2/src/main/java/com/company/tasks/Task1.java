package com.company.tasks;

import java.util.ArrayList;
import java.util.List;


/*
Дан список дат (строковая запись) в форматах типа “2020/04/05”, “05/04/2020”, “04-05-2020” (все даты в примере - 5е апреля 2020)
Вернуть список дат (строковая запись) в формате “20200405”. Даты с неверным форматом - игнорировать.
 */

public class Task1 {
    public static List<String> task1(List<String> dates){
        List<String> result = new ArrayList<>();
        for (String date : dates){
            if(date.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")){
                String year = date.substring(0, 4);
                String month = date.substring(5, 7);
                String day = date.substring(8, 10);
                if(validateDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year))){
                    result.add(year + month + day);
                }
            }
            else if(date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
                String day = date.substring(0, 2);
                String month = date.substring(3, 5);
                String year = date.substring(6, 10);
                if(validateDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year))){
                    result.add(year + month + day);
                }
            }
            else if(date.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")){
                String month = date.substring(0, 2);
                String day = date.substring(3, 5);
                String year = date.substring(6, 10);
                if(validateDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year))){
                    result.add(year + month + day);
                }
            }
        }
        return result;
    }

    private static boolean validateDate(int day, int month, int year){
        boolean isLeap = (year % 400 == 0) ? true : (year % 100 == 0) ? false : (year % 4 == 0) ? true : false;
        if(year <= 0 || month <= 0 || day <= 0 || month > 12){
            return false;
        }
        if((month == 2 && isLeap) && day > 29){
            return false;
        }
        if((month == 2 && !isLeap) && day > 28){
            return false;
        }
        if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31){
            return false;
        }
        if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30){
            return false;
        }
        return true;
    }
}
