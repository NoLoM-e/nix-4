package com.company.entity;


import com.company.anot.ColumnName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @ColumnName("id")
    public long id;

    @ColumnName("name")
    public String name;

    @ColumnName("email")
    public String email;

    @ColumnName("age")
    public int age;
}
