package com.company.dao.impl;

import com.company.dao.LibService;
import com.company.db.CSVDB;
import com.company.db.DBInMemory;
import com.company.entity.Author;
import com.company.entity.Book;
import org.apache.log4j.Logger;

import java.util.List;

public class AuthorService implements LibService<Author> {


    private final Logger logger = Logger.getLogger(AuthorService.class);
    private final CSVDB db = CSVDB.getInstance();


    @Override
    public void create(Author author) {
        try {
            logger.info("create author start");
            if (db.existById(author.getId(), Author.class)) {
                logger.error("author already exist");
                throw new RuntimeException("author already exist");
            }
            logger.info("create author finish");
            db.createAuthor(author);
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Author author) {
        try {
            logger.info("update author start");
            if (!db.existById(author.getId(), Author.class)) {
                logger.error("author does not exist");
                throw new RuntimeException("author does not exist");
            }

            logger.info("update author finish");
            db.updateAuthor(author);
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            logger.info("delete author start");
            db.deleteAuthor(id);
            logger.info("delete author finish");
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Author findById(String id) {
        try {
            logger.info("finding by id author start");
            Author author = db.stringArrayToAuthor(db.findAuthorById(id));

            if (author == null) {
                logger.error("no author found");
                throw new RuntimeException("no author found");
            }

            logger.info("finding by id author finish");
            return author;
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Author> findAll() {
        try {
            logger.info("finding all authors start");
            List<Author> authors = db.getAuthors();

            if (authors == null) {
                logger.error("no author found");
                throw new RuntimeException("no author found");
            }

            logger.info("finding all authors finish");
            return authors;
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Author> findByBook(Book book) {
        try {
            logger.info("finding authors by book start");
            logger.info("finding authors by book finish");
            return db.findAuthorsByBook(book);
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        try {
            logger.info("finding books by author start");
            logger.info("finding books by author finish");
            return db.findBooksByAuthor(author);
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }
}
