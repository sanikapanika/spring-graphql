package com.graphql.sanikapanika;

import javax.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String desc;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.EAGER)
    private Author author;

    public Post() {
    }

    public Post( String name, String desc, Author author) {
        this.name = name;
        this.desc = desc;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String toString() {
        return "[id="+ id + ", name=" + name + ", desc=" + desc + "]";
    }
}
