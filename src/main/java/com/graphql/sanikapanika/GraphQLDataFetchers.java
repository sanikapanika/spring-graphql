package com.graphql.sanikapanika;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    private final PostManager postManager;
    private final AuthorManager authorManager;

    public GraphQLDataFetchers(PostManager postManager, AuthorManager authorManager) {
        this.postManager = postManager;
        this.authorManager = authorManager;
    }

    public DataFetcher getPosts() {
        return dataFetchingEnvironment -> postManager.findAll();
    }

    public DataFetcher newPost() {
        return environment -> {
            String nameInput = environment.getArgument("name");
            String descInput = environment.getArgument("desc");
            Long authorIdInput = environment.getArgument("author");

            return postManager.newPost(nameInput, descInput, authorIdInput);
        };
    }

    public DataFetcher getAuthors() {
        return dataFetchingEnvironment -> authorManager.findAll();
    }

    public DataFetcher newAuthor() {
        return environment -> {
            String nameInput = environment.getArgument("name");

            return authorManager.newAuthor(nameInput);
        };
    }
}
