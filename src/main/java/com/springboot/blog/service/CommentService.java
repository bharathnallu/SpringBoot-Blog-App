package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    //1. Define the abstract method for creating a comment
    CommentDto createComment(long postId, CommentDto commentDto);

    //2. Define abstract method for list of all comments for a specific post
    List<CommentDto> getCommentsByPostId(long postId);

    //3. Define abstract method to get comment by postId if it belongs to that post
    CommentDto getCommentById(long postId, long commentId);

    //4. Define abstract method to update the comment by postId if it belongs to that post
    CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest);

    //5. Define an abstract method to delete the comment by postId if it belongs to that post
    void deleteCommentById(long postId, long commentId);


}
