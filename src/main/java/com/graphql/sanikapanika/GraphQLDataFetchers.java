package com.graphql.sanikapanika;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    private PostRepository postRepository;

    public GraphQLDataFetchers(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public DataFetcher getPosts() {
        return dataFetchingEnvironment -> postRepository.findAll();
    }

    public DataFetcher newPost() {
        return environment -> {
            String nameInput = environment.getArgument("name");
            String descInput = environment.getArgument("desc");

            return postRepository.save(new Post(nameInput, descInput));
        };
    }
}
