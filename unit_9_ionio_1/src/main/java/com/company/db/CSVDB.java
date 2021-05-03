package com.company.db;

import com.company.entity.Author;
import com.company.entity.BaseEntity;
import com.company.entity.Book;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CSVDB {

    private static CSVDB csv;

    private final String authorsFileName = "authors.csv";
    private final String booksFileName = "books.csv";

    private List<String[]> csvAuthorsData;
    private List<String[]> csvBooksData;

    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    private CSVDB(){
        csvAuthorsData = new ArrayList<>();
        csvBooksData = new ArrayList<>();

        csvAuthorsData.add(new String[]{"id", "created", "name", "surname", "books"});
        csvBooksData.add(new String[]{"id", "created", "name", "authors"});

        File booksFile = new File(booksFileName);
        try {
            booksFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File authorsFile = new File(authorsFileName);
        try {
            authorsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            writeBooksToFiles();
            writeAuthorsToFiles();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static CSVDB getInstance(){
        if(csv == null){
            csv = new CSVDB();
        }
        return csv;
    }


    private <T extends BaseEntity> String generateId(String id, List<T> list){
        if(list.stream().anyMatch(t -> t.getId().equals(id))){
            return generateId(UUID.randomUUID().toString(), list);
        }
        return id;
    }


    public <T extends BaseEntity> boolean existById(String id, Class<T> c) throws ParseException, IOException, CsvException {
        if(c.isAssignableFrom(Author.class)){
            return getAuthors().stream().anyMatch(author -> author.getId().equals(id));
        }
        if(c.isAssignableFrom(Book.class)){
            return getBooks().stream().anyMatch(book -> book.getId().equals(id));
        }
        else {
            throw new RuntimeException("class does not exist");
        }
    }

    public void createAuthor(Author author) throws ParseException, IOException, CsvException {
        List<Author> authors = getAuthors();

        String[] data = new String[5];

        data[0] = generateId(UUID.randomUUID().toString(), authors);
        data[1] = String.valueOf(author.getCreated());
        data[2] = author.getName();
        data[3] = author.getSurname();

        StringBuilder stringBuilder = new StringBuilder();

        if(author.getBooks() != null) {
            for (Book book : author.getBooks()) {
                stringBuilder.append(book.getId());
                stringBuilder.append(" ");
            }

            data[4] = stringBuilder.toString();
        }

        csvAuthorsData.add(data);
        writeAuthorsToFiles();
    }

    public void updateAuthor(Author author) throws IOException, CsvException {
        String[] a = findAuthorById(author.getId());
        a[2] = author.getName();
        a[3] = author.getSurname();
        StringBuilder stringBuilder = new StringBuilder();
        if(author.getBooks() != null) {
            for (Book b : author.getBooks()) {
                stringBuilder.append(b.getId());
                stringBuilder.append(" ");
            }
            a[4] = stringBuilder.toString();
        }
        writeAuthorsToFiles();
    }

    public void deleteAuthor(String id) throws IOException, CsvException, ParseException {

        try(CSVReader csvReader = new CSVReader(new FileReader(authorsFileName))) {
            csvAuthorsData = csvReader.readAll();
        }

        csvAuthorsData = csvAuthorsData.stream().filter(strings -> !strings[0].equals(id)).collect(Collectors.toList());
        writeAuthorsToFiles();
    }

    public String[] findAuthorById(String id) throws IOException, CsvException {
        try(CSVReader csvReader = new CSVReader(new FileReader(authorsFileName))) {
            csvAuthorsData = csvReader.readAll();
        }

        String[] s = csvAuthorsData.stream().filter(strings -> strings[0].equals(id)).findFirst().orElse(null);
        if(s == null){
            throw new RuntimeException("author not found");
        }
        return s;
    }

    public List<Author> findAuthorsByBook(Book book) throws ParseException, IOException, CsvException {
        return getAuthors()
                .stream()
                .filter(author -> author.getBooks().stream().anyMatch(book1 -> book1.equals(book)))
                .collect(Collectors.toList());
    }

    public void createBook(Book book) throws IOException, ParseException, CsvException {
        List<Book> books = getBooks();

        String[] data = new String[4];

        data[0] = generateId(UUID.randomUUID().toString(), books);
        data[1] = String.valueOf(book.getCreated());
        data[2] = book.getName();

        StringBuilder stringBuilder = new StringBuilder();

        if(book.getAuthors() != null) {
            for (String s : book.getAuthors()) {
                stringBuilder.append(s);
                stringBuilder.append(" ");
            }

            data[3] = stringBuilder.toString();
        }

        csvBooksData.add(data);
        writeBooksToFiles();
    }

    public void updateBook(Book book) throws IOException, CsvException {
        String[] a = findBookById(book.getId());
        a[2] = book.getName();
        StringBuilder stringBuilder = new StringBuilder();
        if(book.getAuthors() != null) {
            for (String s : book.getAuthors()) {
                stringBuilder.append(s);
                stringBuilder.append(" ");
            }
            a[3] = stringBuilder.toString();
        }
        writeBooksToFiles();
    }

    public void deleteBook(String id) throws IOException, CsvException {

        try(CSVReader csvReader = new CSVReader(new FileReader(booksFileName))) {
            csvBooksData = csvReader.readAll();
        }

        csvBooksData = csvBooksData.stream().filter(strings -> !strings[0].equals(id)).collect(Collectors.toList());
        writeBooksToFiles();
    }

    public String[] findBookById(String id) throws IOException, CsvException {
        try(CSVReader csvReader = new CSVReader(new FileReader(booksFileName))) {
            csvBooksData = csvReader.readAll();
        }

        String[] s = csvBooksData.stream().filter(strings -> strings[0].equals(id)).findFirst().orElse(null);
        if(s == null){
            throw new RuntimeException("book not found");
        }
        return s;
    }

    public List<Book> findBooksByAuthor(Author author) throws ParseException, IOException, CsvException {
        return getBooks()
                .stream()
                .filter(book -> book.getAuthors().stream().anyMatch(s -> s.equals(author.getId())))
                .collect(Collectors.toList());
    }

    public List<Book> getBooks() throws IOException, CsvException, ParseException {
        List<String[]> data;
        List<Book> books = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(booksFileName))){
            data = csvReader.readAll();
        }

        for (int i = 1; i < data.size(); i++){
            String[] s = data.get(i);
            Book book = new Book();
            book.setId(s[0]);
            book.setCreated(format.parse(s[1]));
            book.setName(s[2]);
            List<String> authors = new ArrayList<>();
            String[] authorsIds = s[3].split(" ");
            for (String id : authorsIds){
                authors.add(id);
            }
            book.setAuthors(authors);
            books.add(book);
        }
        return books;
    }

    public List<Author> getAuthors() throws IOException, CsvException, ParseException {
        List<String[]> data;
        List<Author> authors = new ArrayList<>();

       try (CSVReader csvReader = new CSVReader(new FileReader(authorsFileName))){
           data = csvReader.readAll();
       }

       for (int i = 1; i < data.size(); i++){
           String[] s = data.get(i);
           Author author = new Author();
           author.setId(s[0]);
           author.setCreated(format.parse(s[1]));
           author.setName(s[2]);
           author.setSurname(s[3]);
           List<Book> books = new ArrayList<>();
           String[] booksIds = s[4].split(" ");
           for (String id : booksIds){
               if(id.equals("")) {
                   break;
               }
               String[] stringBook = findBookById(id);
               Book book = new Book();
               book.setId(stringBook[0]);
               book.setCreated(format.parse(stringBook[1]));
               book.setName(stringBook[2]);
               List<String> authors1 = new ArrayList<>();
               String[] authorsIds = s[3].split(" ");
               for (String id1 : authorsIds){
                   authors1.add(id1);
               }
               book.setAuthors(authors1);
               books.add(book);
           }
           author.setBooks(books);
           authors.add(author);
        }
        return authors;
    }

    private void writeAuthorsToFiles() throws IOException {
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(authorsFileName))) {
            csvWriter.writeAll(csvAuthorsData);
        }
    }

    private void writeBooksToFiles() throws IOException {
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(booksFileName))) {
            csvWriter.writeAll(csvBooksData);
        }
    }

    private String[] authorToStringArray(Author author){
        String[] s = new String[5];
        s[0] = author.getId();
        s[1] = String.valueOf(author.getCreated());
        s[2] = author.getName();
        s[3] = author.getSurname();
        StringBuilder stringBuilder = new StringBuilder();
        for(Book b : author.getBooks()){
            stringBuilder.append(b.getId());
            stringBuilder.append(" ");
        }
        s[4] = stringBuilder.toString();
        return s;
    }

    public Author stringArrayToAuthor(String[] s) throws ParseException, IOException, CsvException {
        Author author = new Author();
        author.setId(s[0]);
        author.setCreated(format.parse(s[1]));
        author.setName(s[2]);
        author.setSurname(s[3]);
        List<Book> books = new ArrayList<>();
        String[] booksIds = s[4].split(" ");
        for (String id : booksIds){
            if(id.equals("")){
                break;
            }
            String[] stringBook = findBookById(id);
            Book book = new Book();
            book.setId(stringBook[0]);
            book.setCreated(format.parse(stringBook[1]));
            book.setName(stringBook[2]);
            List<String> authors1 = new ArrayList<>();
            String[] authorsIds = s[3].split(" ");
            for (String id1 : authorsIds){
                authors1.add(id1);
            }
            book.setAuthors(authors1);
            books.add(book);
        }
        author.setBooks(books);
        return author;
    }

    public Book stringArrayToBook(String[] s) throws ParseException {
        Book book = new Book();
        book.setId(s[0]);
        book.setCreated(format.parse(s[1]));
        book.setName(s[2]);
        List<String> authors = new ArrayList<>();
        String[] authorsIds = s[3].split(" ");
        for (String id : authorsIds){
            authors.add(id);
        }
        book.setAuthors(authors);
        return book;
    }
}
