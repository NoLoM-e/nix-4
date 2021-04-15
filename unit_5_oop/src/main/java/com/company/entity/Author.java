package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class Author extends BaseEntity {
    private String name;
    private String surname;
    private List<Book> books;

    public Author() {
        super();
    }
}
