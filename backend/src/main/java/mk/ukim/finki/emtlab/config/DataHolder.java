package mk.ukim.finki.emtlab.config;

import mk.ukim.finki.emtlab.model.enumeration.Category;
import mk.ukim.finki.emtlab.model.enumeration.Role;
import mk.ukim.finki.emtlab.service.AuthorService;
import mk.ukim.finki.emtlab.service.BookService;
import mk.ukim.finki.emtlab.service.CountryService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataHolder {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CountryService countryService;

    public DataHolder(AuthorService authorService, BookService bookService, CountryService countryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.countryService = countryService;
    }

    @PostConstruct
    public void initData(){
        countryService.save("Macedonia", "Europe");
        countryService.save("Russia", "Europe");
        countryService.save("China", "Asia");
        countryService.save("India", "Asia");
        countryService.save("Iceland", "Europe");
        authorService.save("Elena", "Maneva", 1L);
        authorService.save("Ana", "Andova", 2L);
        authorService.save("Iva", "Ivanova", 3L);
        authorService.save("Angel", "Angelov", 4L);
        authorService.save("Petar", "Petrov", 5L);
        bookService.save("Little Women", Category.NOVEL, 1L, 4);
        bookService.save("The Girl on the Train", Category.THRILLER, 2L, 10);
        bookService.save("The Lessons of History", Category.HISTORY, 3L, 1);
        bookService.save("A hero born", Category.FANTASY, 4L, 5);
        bookService.save("The Immortal Life of Henrietta Lacks", Category.BIOGRAPHY, 5L, 6);
    }
}
