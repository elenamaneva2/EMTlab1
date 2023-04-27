package mk.ukim.finki.emtlab.service;

import mk.ukim.finki.emtlab.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> listAll();

    Author save(String name, String surname, Long countryId);
}