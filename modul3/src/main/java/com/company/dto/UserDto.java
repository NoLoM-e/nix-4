package com.company.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private long id;
    private String name;
    private String email;
    private String tel;
}
