package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseEntity
{
    private String id;
    private Date created;

    public BaseEntity()
    {
        this.created = new Date();
    }
}
