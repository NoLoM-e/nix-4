package com.company.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;

    @ManyToMany(mappedBy = "operations")
    private List<Category> categories;

    public void addCategory(Category category){
        this.categories.add(category);
        category.getOperations().add(this);
    }

    private Instant commited;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
