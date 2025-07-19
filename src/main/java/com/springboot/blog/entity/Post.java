package com.springboot.blog.entity;

// This is our model/entity/domain class where we define the structure of the "Post" entity in our application

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

// Lombok annotations to generate boilerplate code:
// @Data generates getters, setters, toString(), equals(), and hashCode()
// @NoArgsConstructor generates a no-args constructor
// @AllArgsConstructor generates a constructor with all fields
@Data
@NoArgsConstructor
@AllArgsConstructor

// Marks this class as a JPA entity (a table in the database)
@Entity

// Maps this entity to the "posts" table and sets a unique constraint on the "title" column
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {

    // Marks this field as the primary key
    @Id

    // Specifies that the ID should be generated automatically using the database's identity strategy (e.g., auto-increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Maps this field to a column named "title" and sets it as non-nullable
    @Column(name = "title", nullable = false)
    private String title;

    // Maps this field to a column named "description" and sets it as non-nullable
    @Column(name = "description", nullable = false)
    private String description;

    // Maps this field to a column named "content" and sets it as non-nullable
    @Column(name = "content", nullable = false)
    private String content;

    // This creates a set of comments and also enables for bidirection relationship
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true )       // This enables bidirection relationship
    private Set<Comment> comments = new HashSet<>();
}
