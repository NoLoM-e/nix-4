package com.company.entity;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Route extends AbsEntity{

    private int fromId;
    private int toId;
    private int cost;
}
