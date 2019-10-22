package com.graphql.sanikapanika;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("graphql/schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWriting();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWriting() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetchers(
                                this.createQueryMap()
                        )
                )
                .type(newTypeWiring("Mutation")
                        .dataFetchers(
                                this.createMutationMap()
                        )
                )
                .build();
    }

    private LinkedHashMap<String, DataFetcher> createQueryMap() {
        LinkedHashMap<String, DataFetcher> queryExecutorMap = new LinkedHashMap<>();
        queryExecutorMap.put("allPosts", graphQLDataFetchers.getPosts());
        queryExecutorMap.put("allAuthors", graphQLDataFetchers.getAuthors());

        return queryExecutorMap;
    }

    private LinkedHashMap<String, DataFetcher> createMutationMap() {
        LinkedHashMap<String, DataFetcher> mutationExecutorMap = new LinkedHashMap<>();
        mutationExecutorMap.put("newPost", graphQLDataFetchers.newPost());
        mutationExecutorMap.put("newAuthor", graphQLDataFetchers.newAuthor());
        mutationExecutorMap.put("newUser", graphQLDataFetchers.newUser());
        mutationExecutorMap.put("confirmUser", graphQLDataFetchers.confirmUser());

        return mutationExecutorMap;
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
