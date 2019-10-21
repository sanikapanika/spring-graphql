package com.graphql.sanikapanika;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostManager {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostManager(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post newPost(String name, String desc, Long authorId) throws Exception {
        Optional<Author> author = authorRepository.findById(authorId);

        if(author.isEmpty()) {
            throw new Exception("Author does not exist");
        }

        return postRepository.save(new Post(name, desc, author.get()));
    }
}
