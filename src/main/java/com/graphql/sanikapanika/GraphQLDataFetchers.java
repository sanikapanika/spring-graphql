package com.graphql.sanikapanika;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GraphQLDataFetchers {

    private PostRepository postRepository;
    private AuthorRepository authorRepository;

    public GraphQLDataFetchers(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public DataFetcher getPosts() {
        return dataFetchingEnvironment -> postRepository.findAll();
    }

    public DataFetcher newPost() {
        return environment -> {
            String nameInput = environment.getArgument("name");
            String descInput = environment.getArgument("desc");
            Long authorIdInput = environment.getArgument("author");

            Optional<Author> author = this.authorRepository.findById(authorIdInput);
            if(!author.isPresent()) {
                throw new Exception("Author does not exist");
            }

            return postRepository.save(new Post(nameInput, descInput, author.get()));
        };
    }

    public DataFetcher getAuthors() {
        return dataFetchingEnvironment -> authorRepository.findAll();
    }

    public DataFetcher newAuthor() {
        return environment -> {
            String nameInput = environment.getArgument("name");

            Author author = new Author(nameInput);

            return this.authorRepository.save(author);
        };
    }
}
