package com.graphql.sanikapanika;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    private final PostManager postManager;
    private final AuthorManager authorManager;
    private final UserManager userManager;

    public GraphQLDataFetchers(PostManager postManager, AuthorManager authorManager, UserManager userManager) {
        this.postManager = postManager;
        this.authorManager = authorManager;
        this.userManager = userManager;
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

    public DataFetcher newUser() {
        return environment -> {
            String usernameInput = environment.getArgument("username");
            String rawPassInput = environment.getArgument("rawPassword");

            return userManager.registerUser(usernameInput, rawPassInput);
        };
    }

    public DataFetcher confirmUser() {
        return environment -> {
            String usernameInput = environment.getArgument("username");

            return userManager.confirmUser(usernameInput);
        };
    }
}
