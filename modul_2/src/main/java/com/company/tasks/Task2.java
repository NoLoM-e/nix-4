package com.company.tasks;


import java.util.ArrayList;


/*
Дан список имен.
Найти первое уникальное имя.
Допустимая временная сложность - O(n) при условии, что доступ к элементу списка по индексу - O(1).
 */
public class Task2 {
    public static String task2(ArrayList<String> names){
        for(int i = 0; i < names.size(); i++){
            if(names.lastIndexOf(names.get(i)) == i){
                return names.get(i);
            }
        }
        return "";
    }
}


