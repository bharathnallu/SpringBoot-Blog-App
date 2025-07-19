package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    // Inject the Service bean here
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //1. REST API end point for create comment
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

    }

    //2. REST API endpoint for get all comments
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    //3. REST API endpoint to get the comment by commentId if it belongs to the PostId
    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long PostId,
                                                     @PathVariable(value = "id") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(PostId, commentId), HttpStatus.OK);

    }

    //4. REST API endpoint to update the comment by commentId if it belongs to the PostId
    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById( @PathVariable(value = "postId") Long postId,
                                                         @PathVariable(value ="id") Long commentId,
                                                         @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentDto), HttpStatus.OK);

    }

    //5. REST API endpoint to delete the comment by commentId if it belongs to the PostId
    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "id")long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
