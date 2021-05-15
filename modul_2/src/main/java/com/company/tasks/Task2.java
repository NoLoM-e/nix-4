package com.company.tasks;


import java.util.*;


/*
Дан список имен.
Найти первое уникальное имя.
Допустимая временная сложность - O(n) при условии, что доступ к элементу списка по индексу - O(1).
 */
public class Task2 {
    public static String task2(ArrayList<String> names){

        Map<String, Integer> map = new LinkedHashMap<>();

        for (String name : names){

            if(map.containsKey(name)){
                map.replace(name, map.get(name) + 1);
            }
            else {
                map.put(name, 1);
            }
        }

        for (String name : names){

            if(map.get(name) == 1){
                return name;
            }
        }

        return "";
    }
}


