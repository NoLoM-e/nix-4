package com.company.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Data {

    private Map<String, Integer> names;
    private List<String[]> data;

    public Data(String[] names, List<String[]> data){
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], i);
        }
        this.names = map;
        this.data = data;
    }

    public String get(int row, int column){
        try {
            return data.get(row)[column];
        }catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Wrong index");
        }
    }

    public String get(int row, String column){
        Integer c = names.get(column);
        if(c != null)
            return get(row, c);
        else
            throw new RuntimeException("column not found");
    }

    public String[] getNames(){
        return names.keySet().toArray(new String[0]);
    }

    public int rowCount(){
        return data.size();
    }
}
