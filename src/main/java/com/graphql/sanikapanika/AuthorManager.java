package com.graphql.sanikapanika;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorManager {

    private final AuthorRepository authorRepository;

    public AuthorManager(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author newAuthor(String name) {
        return authorRepository.save(new Author(name));
    }
}
