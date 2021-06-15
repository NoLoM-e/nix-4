package com.company.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationDto {

    private Long id;
    private double value;
    private List<CategoriesDto> categories;
    private Instant commited;
    private AccountDto account;
}
