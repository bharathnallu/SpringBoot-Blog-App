package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

/**
 * This interface defines the contract for Post-related business logic.
 * It will be implemented by a service class (e.g., PostServiceImpl).
 *
 * All the methods declared here are abstract by default and must be implemented
 * in the service layer to handle the actual processing and interaction with the repository.
 */
public interface PostService {

    /**
     * Creates a new blog post.
     *
     * @param postDto the post data sent from the client
     * @return the created post as a DTO object
     */
    PostDto createPost(PostDto postDto);

    /**
     * Retrieves a paginated list of all blog posts.
     *
     * @param pageNo the page number to retrieve (starts from 0 or 1 depending on implementation)
     * @param pageSize the number of records per page
     * @return a PostResponse object that contains the list of PostDto and pagination details
     */
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Retrieves a single blog post by its unique ID.
     *
     * @param id the ID of the post to retrieve
     * @return the found PostDto object
     */
    PostDto getPostById(Long id);

    /**
     * Updates an existing blog post with new data.
     *
     * @param postDto the new post data to be updated
     * @param id the ID of the post to update
     * @return the updated PostDto object
     */
    PostDto updatePost(PostDto postDto, Long id);

    /**
     * Deletes a blog post by its ID.
     *
     * @param id the ID of the post to delete
     */
    void deletePostById(Long id);
}
