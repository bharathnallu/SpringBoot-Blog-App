package com.springboot.blog.payload;

// This DTO (Data Transfer Object) class is used to transfer data between processes.
// It encapsulates only the required fields for the Post resource and hides the internal structure of the entity.

import lombok.Data;

// Lombok @Data annotation automatically generates:
// - Getters and setters for all fields
// - toString(), equals(), and hashCode() methods
// This helps reduce boilerplate code and keeps the class clean.
@Data
public class PostDto {

    // Unique identifier of the post
    private Long id;

    // Title of the post
    private String title;

    // Short description or summary of the post
    private String description;

    // Full content/body of the post
    private String content;
}
