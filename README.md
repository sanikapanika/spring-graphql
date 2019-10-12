# Humble GraphQL Spring Boot implementation 

## Prerequisites
1. Java 12 (should work with >= 9)
2. Maven 3.6.1 (lower not tested)

## Usage
1. Start app
2. Hit route `localhost:8080/graphiql`
3. Two models are available (User and Post)
4. See Query and Mutation section and have fun (Look right)
5. Have fun

## Further info
Schema is located at `src/main/resources/graphql/schema.graphqls`. Create entities in app as usual, repos and services, then register them in `GraphQLDataFetchers`. Map methods from `GraphQLDataFetchers` to your new defined queries from `schema.graphqls` in `GraphQLProvider` (see current impl as example). Do that by adding query/mutation to their respective position in the provider, just put `(query/mutation)ExecutorMap.put("<name from schema>", graphQLDataFetchers.<your method name>());`. Everything else gets wired at runtime, restart the app and try them out.

## Background
I recently started learning graphql and saw that the documentation is not very rich with implementation details for starters so I found some way of implementing the base to start learning, so you dont lose the will to continue :D
Some nice documentation after you get the hang of how things are wired can be found ![here](https://www.graphql-java.com/documentation/v12/), just pay attention to the version of `graphql-java` you are using (project default is 12 even though pom.xml says 11, trust me and don't ask why) 
