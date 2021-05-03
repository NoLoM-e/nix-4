package com.company.dao;

import com.company.entity.Author;
import com.company.entity.BaseEntity;
import com.company.entity.Book;

import java.util.List;

public interface LibService<T extends BaseEntity> {

    void create(T t);
    void update(T t);
    void delete(String id);
    T findById(String id);
    List<T> findAll();
    List<Author> findByBook(Book book);
    List<Book> findByAuthor(Author author);
}
