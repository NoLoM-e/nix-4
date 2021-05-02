package com.company.entity;


import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Comparable<User> {

    private String name;
    private int age;

    @Override
    public int compareTo(User user) {
        return this.age - user.getAge();
    }
}
