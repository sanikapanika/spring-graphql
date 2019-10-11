package com.graphql.sanikapanika;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SanikapanikaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanikapanikaApplication.class, args);
    }

    @Bean
    public CommandLineRunner dummyData(PostRepository postRepository) {
        return (args -> {
            Post post = new Post("Kurac", "kurcina");
            postRepository.save(post);
        });
    }
}
