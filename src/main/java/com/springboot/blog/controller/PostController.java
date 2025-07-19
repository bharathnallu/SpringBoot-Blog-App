package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller where every method returns a domain object instead of a view
@RestController

// Maps incoming HTTP requests to /api/posts to the methods in this controller
@RequestMapping("/api/posts")
public class PostController {

    // Step 1: Inject the PostService interface to handle the business logic
    private final PostService postService;

    // Step 2: Constructor-based dependency injection
    // Since there’s only one constructor, Spring will autowire this automatically (Spring 4.3+)
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Step 3: REST endpoint to create a blog post
    // @PostMapping handles HTTP POST requests
    // @RequestBody maps the incoming request body to a PostDto object
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        // Returns 201 CREATED status with the created PostDto as the response body
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Step 4: REST endpoint to retrieve all blog posts
    // @GetMapping handles HTTP GET requests
    // Updated for Pagination and Sorting
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //Step 5: REST endpoint to retrieve post by id
    // @GetMapping handles HTTP GET requests
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Step 6: REST endpoint to update post by id
    //@PutMapping handles HTTP update requests
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id){

        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Step 7: REST endpoint to delete post by id
    //@DeleteMapping handles HTTP delete requests
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }



}
