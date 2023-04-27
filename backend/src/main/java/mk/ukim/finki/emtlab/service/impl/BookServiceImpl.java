package mk.ukim.finki.emtlab.service.impl;


import mk.ukim.finki.emtlab.model.Author;
import mk.ukim.finki.emtlab.model.Book;
import mk.ukim.finki.emtlab.model.dto.BookDto;
import mk.ukim.finki.emtlab.model.enumeration.Category;
import mk.ukim.finki.emtlab.model.exception.AuthorNotFoundException;
import mk.ukim.finki.emtlab.model.exception.BookNotFoundException;
import mk.ukim.finki.emtlab.repository.AuthorRepository;
import mk.ukim.finki.emtlab.repository.BookRepository;
import mk.ukim.finki.emtlab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return Optional.of(book);
    }

    @Override
    public Book save(String name, Category category, Long authorId, Integer availableCopies) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Book book = new Book(name, category, author, availableCopies);
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        Author author = authorRepository.findById(bookDto.getAuthor()).orElseThrow(() ->
                new AuthorNotFoundException(bookDto.getAuthor()));
        return Optional.of(bookRepository.save(new Book(bookDto.getName(), bookDto.getCategory(), author,
                bookDto.getAvailableCopies())));
    }

    @Override
    public Optional<Book> update(Long id, BookDto bookDto) {
        Book book = findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Author author = authorRepository.findById(bookDto.getAuthor()).orElseThrow(() ->
                new AuthorNotFoundException(bookDto.getAuthor()));
        book.setName(bookDto.getName());
        book.setCategory(bookDto.getCategory());
        book.setAuthor(author);
        if(bookDto.getAvailableCopies() < 0){
            bookDto.setAvailableCopies(0);
        }
        book.setAvailableCopies(bookDto.getAvailableCopies());
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> delete(Long id) {
        Book book = findById(id).orElseThrow();
        bookRepository.delete(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> markAsTaken(Long id) {
        Book book = findById(id).orElseThrow();
        if(book.getAvailableCopies() != 0){
            book.setAvailableCopies(book.getAvailableCopies()-1);
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }
}
