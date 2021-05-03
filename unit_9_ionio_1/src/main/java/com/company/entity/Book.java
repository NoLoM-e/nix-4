package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class Book extends BaseEntity{

    private String name;
    private List<String> authors;

    public Book(){
        super();
    }
}
