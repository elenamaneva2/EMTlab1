package mk.ukim.finki.emtlab.web;

import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.service.AuthorService;
import mk.ukim.finki.emtlab.service.BookService;
import mk.ukim.finki.emtlab.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/books")
public class RestBookController {
    private final BookService bookService;
    private final AuthorService authorService;
    public final CountryService countryService;

    public RestBookController(BookService bookService, AuthorService authorService, CountryService countryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @GetMapping
    public List<Book> listBooks(){
        return bookService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Book> add(@RequestBody BookDto bookDto){
        return bookService.save(bookDto)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return this.bookService.update(id, bookDto)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if(bookService.delete(id).isPresent()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/markAsTaken/{id}")
    public ResponseEntity<Book> markAsTaken(@PathVariable Long id){
        return this.bookService.markAsTaken(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
