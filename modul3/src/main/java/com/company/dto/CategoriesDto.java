package com.company.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CategoriesDto {

    private long id;
    private boolean isIncome;
    private String name;
}
