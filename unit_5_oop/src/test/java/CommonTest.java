import com.company.dao.LibService;
import com.company.dao.impl.AuthorService;
import com.company.dao.impl.BookService;
import com.company.entity.Author;
import com.company.entity.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonTest {

    private static final LibService<Author> authorService = new AuthorService();
    private static final LibService<Book> bookService = new BookService();

    @BeforeAll
    public static void init(){
        for (int i = 0; i < 10; i++) {
            String s = "test" + i;
            Author author = new Author();
            author.setName(s);
            author.setSurname(s + s);
            authorService.create(author);
        }
        Assert.assertEquals(authorService.findAll().size(), 10);

        for (int i = 0; i < 10; i++) {
            String s = "test" + i;
            Book book = new Book();
            book.setName(s);
            book = setAuthorToBook(book, s, s + s);
            bookService.create(book);
        }
        Assert.assertEquals(bookService.findAll().size(), 10);
    }
    
    @Test
    @Order(1)
    public void createAuthor(){
        int previous = authorService.findAll().size();

        String name = "hello";
        String surname = "world";
        Author author = new Author();
        author.setName(name);
        author.setSurname(surname);
        authorService.create(author);

        Author a = authorService.findById(
                authorService.findAll().stream()
                .filter(author1 -> author1.getName().equals(name) && author1.getSurname().equals(surname))
                        .findFirst()
                        .get().getId());

        Assert.assertTrue(a != null);

        int last = authorService.findAll().size();

        Assert.assertEquals(previous + 1, last);
    }


    @Test
    @Order(2)
    public void updateAuthor(){

        createAuthor();

        String name = "hello";
        String surname = "world";
        String name1 = "world";
        String surname1 = "hello";

        List<Author> authors = authorService.findAll();
        Author a = authorService.findById(
                authorService.findAll().stream()
                        .filter(author1 -> author1.getName().equals(name) && author1.getSurname().equals(surname))
                        .findFirst()
                        .get().getId());

        a.setName(name1);
        a.setSurname(surname1);


        authorService.update(a);

        a = authorService.findById(
                authorService.findAll().stream()
                        .filter(author1 -> author1.getName().equals(name1) && author1.getSurname().equals(surname1))
                        .findFirst()
                        .get().getId());

        Assert.assertEquals(a.getName(), "world");
        Assert.assertEquals(a.getSurname(), "hello");
    }

    @Test
    @Order(3)
    public void deleteAuthor(){
        createAuthor();

        int previous = authorService.findAll().size();

        String name = "hello";
        String surname = "world";

        Author a = authorService.findById(
                authorService.findAll().stream()
                        .filter(author1 -> author1.getName().equals(name) && author1.getSurname().equals(surname))
                        .findFirst()
                        .get().getId());

        authorService.delete(a.getId());

        int last = authorService.findAll().size();

        Assert.assertEquals(previous - 1, last);
    }


    @Test
    @Order(4)
    public void createBook(){
        int previous = bookService.findAll().size();

        String name = "hello";
        Book book = new Book();
        book.setName(name);
        bookService.create(book);

        Book a = bookService.findById(
                bookService.findAll().stream()
                        .filter(book1 -> book1.getName().equals(name))
                        .findFirst()
                        .get().getId());

        Assert.assertTrue(a != null);

        int last = bookService.findAll().size();

        Assert.assertEquals(previous + 1, last);
    }

    @Test
    @Order(5)
    public void updateBook(){
        createBook();

        String name = "hello";
        String name1 = "world";

        Book a = bookService.findById(
                bookService.findAll().stream()
                        .filter(book -> book.getName().equals(name))
                        .findFirst()
                        .get().getId());

        a.setName(name1);

        bookService.update(a);

        a = bookService.findById(
                bookService.findAll().stream()
                        .filter(book -> book.getName().equals(name1))
                        .findFirst()
                        .get().getId());

        Assert.assertEquals(a.getName(), "world");
    }

    @Test
    @Order(6)
    public void deleteBook(){
        createBook();

        int previous = bookService.findAll().size();

        String name = "hello";

        Book a = bookService.findById(
                bookService.findAll().stream()
                        .filter(book -> book.getName().equals(name))
                        .findFirst()
                        .get().getId());

        bookService.delete(a.getId());

        int last = bookService.findAll().size();

        Assert.assertEquals(previous - 1, last);
    }


    private static Book setAuthorToBook(Book book, String name, String surname){
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
