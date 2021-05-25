package com.company.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Problem extends AbsEntity{

    private int toId;
    private int fromId;
}
