package com.company.dao.impl;

import com.company.dao.LibService;
import com.company.db.DBInMemory;
import com.company.entity.Author;
import com.company.entity.Book;

import java.util.List;

public class BookService implements LibService<Book> {


    private final DBInMemory db = DBInMemory.getInstance();


    @Override
    public void create(Book book) {
        if(db.existById(book.getId(), Book.class)){
            throw new RuntimeException("book already exist");
        }

        db.createBook(book);
    }

    @Override
    public void update(Book book) {
        if(!db.existById(book.getId(), Book.class)){
            throw new RuntimeException("book does not exist");
        }

        db.updateBook(book);
    }

    @Override
    public void delete(String id) {
        db.deleteBook(id);
    }

    @Override
    public Book findById(String id) {
        Book book = db.findBookById(id);

        if(book == null){
            throw new RuntimeException("no book found");
        }

        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = db.getBooks();

        if(books == null){
            throw new RuntimeException("no book found");
        }

        return books;
    }

    @Override
    public List<Author> findByBook(Book book) {
        return db.findAuthorsByBook(book);
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return db.findBooksByAuthor(author);
    }
}
