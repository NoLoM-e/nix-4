package com.company.dao.impl;

import com.company.dao.LibService;
import com.company.db.DBInMemory;
import com.company.entity.Author;
import com.company.entity.Book;
import org.apache.log4j.Logger;

import java.util.List;

public class AuthorService implements LibService<Author> {


    private final Logger logger = Logger.getLogger(AuthorService.class);
    private final DBInMemory db = DBInMemory.getInstance();


    @Override
    public void create(Author author) {
        logger.info("create author start");
        if(db.existById(author.getId(), Author.class)){
            logger.error("author already exist");
            throw new RuntimeException("author already exist");
        }

        logger.info("create author finish");
        db.createAuthor(author);
    }

    @Override
    public void update(Author author) {
        logger.info("update author start");
        if(!db.existById(author.getId(), Author.class)){
            logger.error("author does not exist");
            throw new RuntimeException("author does not exist");
        }

        logger.info("update author finish");
        db.updateAuthor(author);
    }

    @Override
    public void delete(String id) {
        logger.info("delete author start");
        db.deleteAuthor(id);
        logger.info("delete author finish");
    }

    @Override
    public Author findById(String id) {
        logger.info("finding by id author start");
        Author author = db.findAuthorById(id);

        if(author == null){
            logger.error("no author found");
            throw new RuntimeException("no author found");
        }

        logger.info("finding by id author finish");
        return author;
    }

    @Override
    public List<Author> findAll() {
        logger.info("finding all authors start");
        List<Author> authors = db.getAuthors();

        if(authors == null){
            logger.error("no author found");
            throw new RuntimeException("no author found");
        }

        logger.info("finding all authors finish");
        return authors;
    }

    @Override
    public List<Author> findByBook(Book book) {
        logger.info("finding authors by book start");
        logger.info("finding authors by book finish");
        return db.findAuthorsByBook(book);
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        logger.info("finding books by author start");
        logger.info("finding books by author finish");
        return db.findBooksByAuthor(author);
    }
}
