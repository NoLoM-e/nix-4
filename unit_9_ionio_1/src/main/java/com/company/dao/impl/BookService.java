package com.company.dao.impl;

import com.company.dao.LibService;
import com.company.db.CSVDB;
import com.company.db.DBInMemory;
import com.company.entity.Author;
import com.company.entity.Book;
import org.apache.log4j.Logger;

import java.util.List;

public class BookService implements LibService<Book> {


    private final Logger logger = Logger.getLogger(BookService.class);
    private final CSVDB db = CSVDB.getInstance();


    @Override
    public void create(Book book) {
        try {
            logger.info("create book start");
            if (db.existById(book.getId(), Book.class)) {
                logger.error("book already exist");
                throw new RuntimeException("book already exist");
            }

            logger.info("create book finish");
            db.createBook(book);
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Book book) {
        try {
            logger.info("update book start");
            if (!db.existById(book.getId(), Book.class)) {
                logger.error("book does not exist");
                throw new RuntimeException("book does not exist");
            }

            logger.info("update book finish");
            db.updateBook(book);
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            logger.info("delete book start");
            db.deleteBook(id);
            logger.info("delete book finish");
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Book findById(String id) {
        try {
            logger.info("find by id book start");
            Book book = db.stringArrayToBook(db.findBookById(id));

            if (book == null) {
                logger.error("no book found");
                throw new RuntimeException("no book found");
            }

            logger.info("find by id book finish");
            return book;
        } catch (Exception e){
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        try {
            logger.info("find all books start");
            List<Book> books = db.getBooks();

            if (books == null) {
                logger.error("no book found");
                throw new RuntimeException("no book found");
            }

            logger.info("find all books finish");
            return books;
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
