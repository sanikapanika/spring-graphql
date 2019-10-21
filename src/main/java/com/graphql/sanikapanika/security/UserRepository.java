package com.graphql.sanikapanika.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getFirstByUsername(String username);
}
