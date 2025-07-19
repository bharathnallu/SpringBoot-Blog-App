package com.springboot.blog.entity;

// This class is for Comment Resource

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)   // Many comments are associated to one post i.e, ManyToOne
                                        // Fetch Type lazy tells Hibernate to only Fetch the related entities
                                        // from the database when you use the relationship
    @JoinColumn(name = "post_id", nullable = false)         // This makes the post_id field as a foreign key in Comment Resource ER-Diagram
    private Post post;
}
