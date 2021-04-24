package com.company.db;

import com.company.entity.Author;
import com.company.entity.BaseEntity;
import com.company.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DBInMemory {

    private final List<Author> authors;
    private final List<Book> books;

    private static DBInMemory db;

    private DBInMemory(){

        authors = new ArrayList<>();
        books = new ArrayList<>();
    }

    public static DBInMemory getInstance(){

        if(db == null){
            db = new DBInMemory();
        }

        return db;
    }

    private <T extends BaseEntity> String generateId(String id, List<T> list){
        if(list.stream().anyMatch(t -> t.getId().equals(id))){
            return generateId(UUID.randomUUID().toString(), list);
        }
        return id;
    }

    public <T extends BaseEntity> boolean existById(String id, Class<T> c){
        if(c.isAssignableFrom(Author.class)){
            return this.authors.stream().anyMatch(author -> author.getId().equals(id));
        }
        if(c.isAssignableFrom(Book.class)){
            return this.books.stream().anyMatch(book -> book.getId().equals(id));
        }
        else {
            throw new RuntimeException("class does not exist");
        }
    }


    public void createAuthor(Author author) {
        author.setId(generateId(UUID.randomUUID().toString(), authors));
        this.authors.add(author);
    }

    public void updateAuthor(Author author){
        Author current = findAuthorById(author.getId());

        current.setName(author.getName());
        current.setSurname(author.getSurname());
        current.setBooks(author.getBooks());
    }

    public void deleteAuthor(String id){
        this.authors.removeIf(author -> author.getId().equals(id));
    }

    public Author findAuthorById(String id){
        Author a = this.authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst()
                .orElse(null);

        if(a == null){
            throw new RuntimeException("author not found");
        }

        return a;
    }

    public List<Author> findAuthorsByBook(Book book){
        return this.authors.stream()
                .filter(author -> author.getBooks().stream().anyMatch(book1 -> book1.getId().equals(book.getId())))
                .collect(Collectors.toList());
    }

    public void createBook(Book book) {
        book.setId(generateId(UUID.randomUUID().toString(), books));
        this.books.add(book);
    }

    public void updateBook(Book book){
        Book current = findBookById(book.getId());

        current.setName(book.getName());
        current.setAuthors(book.getAuthors());
    }

    public void deleteBook(String id){
        this.books.removeIf(book -> book.getId().equals(id));
    }

    public Book findBookById(String id){
        Book a = this.books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);

        if(a == null){
            throw new RuntimeException("book not found");
        }

        return a;
    }

    public List<Book> findBooksByAuthor(Author author){
        return author.getBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getAuthors(){
        return authors;
    }
}
