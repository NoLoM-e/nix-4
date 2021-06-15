package com.company.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDto {

    private long id;
    private UserDto user;
    private double value;
}
