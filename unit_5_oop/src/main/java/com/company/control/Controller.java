package com.company.control;

import com.company.dao.LibService;
import com.company.dao.impl.AuthorService;
import com.company.dao.impl.BookService;
import com.company.entity.Author;
import com.company.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    private static final LibService authorService = new AuthorService();
    private static final LibService bookService = new BookService();

    private static final Scanner in = new Scanner(System.in);

    public void crud(){
        while (true) {
            System.out.println("Choose option");
            System.out.println("exit: 0");
            System.out.println("create: 1");
            System.out.println("read: 2");
            System.out.println("update: 3");
            System.out.println("delete: 4");

            switch (in.nextLine()) {
                case "1":
                    create();
                    break;
                case "2":
                    read();
                    break;
                case "3":
                    update();
                    break;
                case "4":
                    delete();
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("No such option found");
            }
        }
    }

    private void create(){
        System.out.println("Choose option");
        System.out.println("create author: 1");
        System.out.println("create book: 2");
        System.out.println("return: 0");
        switch (in.nextLine()){
            case "1":
                createAuthor();
                break;
            case "2":
                createBook();
                break;
            case "0":
                break;
            default:
                System.out.println("No such option found");
        }
    }

    private void createAuthor(){

        System.out.println("Enter author name");
        String name = in.nextLine();
        System.out.println("Enter author surname");
        String surname = in.nextLine();

        Author author = new Author();
        author.setName(name);
        author.setSurname(surname);

        try {
            authorService.create(author);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private void createBook(){

        Book book = new Book();

        System.out.println("Enter book name");
        String name = in.nextLine();
        book.setName(name);

        System.out.println("Enter number of authors");
        int num = Integer.parseInt(in.nextLine());
        for (int i = 0; i < num; i++) {
            System.out.println("Enter author " + (i + 1) + " name and surname");
            String[] s = in.nextLine().split(" ");
            String authorName = s[0];
            String authorSurname = s[1];

            try {
                book = setAuthorToBook(book, authorName, authorSurname);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        try {
            bookService.create(book);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void read(){

        System.out.println("Choose option");
        System.out.println("get all authors: 1");
        System.out.println("get all books: 2");
        System.out.println("get author by id: 3");
        System.out.println("get book by id: 4");
        System.out.println("get books by author: 5");
        System.out.println("get authors by book: 6");
        System.out.println("return: 0");

        switch (in.nextLine()){
            case "1":
                try {
                    List<Author> authors = authorService.findAll();
                    for (Author a : authors) {
                        System.out.println(authorToString(a));
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                try {
                    List<Book> books = bookService.findAll();
                    for (Book a : books) {
                        System.out.println(bookToString(a));
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "3":
                System.out.println("Enter id");
                try {
                    System.out.println(authorToString((Author) authorService.findById(in.nextLine())));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "4":
                System.out.println("Enter id");
                try {
                    System.out.println(bookToString((Book) bookService.findById(in.nextLine())));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "5":
                System.out.println("Enter author id");
                try {
                    String id = in.nextLine();
                    List<Book> books = bookService.findByAuthor((Author) authorService.findById(id));

                    for (Book b: books) {
                        System.out.println(bookToString(b));
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "6":
                System.out.println("Enter book id");
                try {
                    String id = in.nextLine();
                    List<Author> authors = authorService.findByBook((Book) bookService.findById(id));

                    for (Author a : authors){
                        System.out.println(authorToString(a));
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "0":
                break;
            default:
                System.out.println("No such option found");
        }
    }

    private void update(){
        System.out.println("Choose option");
        System.out.println("update author: 1");
        System.out.println("update book: 2");
        System.out.println("return: 0");

        switch (in.nextLine()){
            case "1":
                System.out.println("Enter author id");
                try {
                    String id = in.nextLine();
                    Author author = (Author) authorService.findById(id);

                    System.out.println("Enter author name");
                    String name = in.nextLine();
                    System.out.println("Enter author surname");
                    String surname = in.nextLine();

                    author.setName(name);
                    author.setSurname(surname);

                    authorService.update(author);

                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                System.out.println("Enter book id");
                try {
                    String id = in.nextLine();
                    Book book = (Book) bookService.findById(id);

                    System.out.println("Enter book name");
                    String name = in.nextLine();
                    book.setName(name);

                    System.out.println("Enter number of authors");
                    book.setAuthors(new ArrayList<>());
                    int num = Integer.parseInt(in.nextLine());
                    for (int i = 0; i < num; i++) {
                        System.out.println("Enter author " + (i + 1) + " name and surname");
                        String[] s = in.nextLine().split(" ");
                        String authorName = s[0];
                        String authorSurname = s[1];

                        book = setAuthorToBook(book, authorName, authorSurname);
                    }

                    bookService.update(book);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case "0":
                break;
            default:
                System.out.println("No such option found");
        }

    }
    private void delete(){
        System.out.println("Choose option");
        System.out.println("delete author: 1");
        System.out.println("delete book: 2");
        System.out.println("return: 0");

        switch (in.nextLine()){
            case "1":
                System.out.println("Enter author id");
                try {
                    String id = in.nextLine();
                    authorService.delete(id);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                System.out.println("Enter book id");
                try {
                    String id = in.nextLine();
                    bookService.delete(id);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "0":
                break;
            default:
                System.out.println("No such option found");
        }
    }


    private String authorToString(Author author){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Id: ");
        stringBuilder.append(author.getId());
        stringBuilder.append("\n");

        stringBuilder.append("Name: ");
        stringBuilder.append(author.getName());
        stringBuilder.append("\n");

        stringBuilder.append("Surname: ");
        stringBuilder.append(author.getSurname());
        stringBuilder.append("\n");

        stringBuilder.append("Books: ");
        List<Book> books = author.getBooks();
        if(books != null) {
            for (Book b : author.getBooks()) {
                stringBuilder.append(b.getName());
                stringBuilder.append("; ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


    private String bookToString(Book book){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Id: ");
        stringBuilder.append(book.getId());
        stringBuilder.append("\n");

        stringBuilder.append("Name: ");
        stringBuilder.append(book.getName());
        stringBuilder.append("\n");

        stringBuilder.append("Authors: ");
        List<String> list = book.getAuthors();
        if(list != null) {
            for (String i : list) {
                Author author = (Author) authorService.findById(i);
                stringBuilder.append(author.getName());
                stringBuilder.append(" ");
                stringBuilder.append(author.getSurname());
                stringBuilder.append("; ");
            }
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    private Book setAuthorToBook(Book book, String name, String surname){
        List<Author> authors = authorService.findAll();

        Author a = authors.stream()
                .filter(author -> author.getName().equals(name) && author.getSurname().equals(surname)).findFirst().orElse(null);

        if(a == null){
            throw new RuntimeException("No such author found");
        }
        else {
            List<String> list = book.getAuthors();
            List<Book> list1 = a.getBooks();

            if(list == null){
                list = new ArrayList<>();
            }

            if(list1 == null){
                list1 = new ArrayList<>();
            }
            list.add(a.getId());
            book.setAuthors(list);

            list1.add(book);
            a.setBooks(list1);
        }

        return book;
    }

}
