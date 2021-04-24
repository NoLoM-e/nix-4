package com.company.dao.impl;

import com.company.dao.LibService;
import com.company.db.DBInMemory;
import com.company.entity.Author;
import com.company.entity.Book;

import java.util.List;

public class AuthorService implements LibService<Author> {


    private final DBInMemory db = DBInMemory.getInstance();


    @Override
    public void create(Author author) {
        if(db.existById(author.getId(), Author.class)){
            throw new RuntimeException("author already exist");
        }

        db.createAuthor(author);
    }

    @Override
    public void update(Author author) {
        if(!db.existById(author.getId(), Author.class)){
            throw new RuntimeException("author does not exist");
        }

        db.updateAuthor(author);
    }

    @Override
    public void delete(String id) {
        db.deleteAuthor(id);
    }

    @Override
    public Author findById(String id) {
        Author author = db.findAuthorById(id);

        if(author == null){
            throw new RuntimeException("no author found");
        }

        return author;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = db.getAuthors();

        if(authors == null){
            throw new RuntimeException("no author found");
        }

        return authors;
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
