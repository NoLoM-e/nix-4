package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Book extends BaseEntity{

    private String name;
    private List<Integer> authors;

    public Book(){
        super();
    }
}
