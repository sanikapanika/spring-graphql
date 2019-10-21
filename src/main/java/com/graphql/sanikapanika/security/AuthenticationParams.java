package com.graphql.sanikapanika.security;

public class AuthenticationParams {
    public String username;

    public String password;

    public AuthenticationParams() {
    }

    public AuthenticationParams(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
