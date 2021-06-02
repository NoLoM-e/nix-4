package com.company.data;


import java.util.List;


public class Data {

    private String[] names;
    private List<String[]> data;

    public Data(String[] names, List<String[]> data){
        this.names = names;
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
        int c = -1;
        for (int i = 0; i < names.length; i++){
            if(names[i].equals(column)){
                c = i;
                break;
            }
        }
        if(c != -1)
            return get(row, c);
        else
            throw new RuntimeException("column not found");
    }

    public String[] getNames(){
        return names;
    }

    public int rowCount(){
        return data.size();
    }
}
