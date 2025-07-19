package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl  implements CommentService {

    // Inject the Comment Repository
    private final CommentRepository commentRepository;

    // Inject Post Repository
    private PostRepository postRepository;




    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToCommentEntity(commentDto);

        //1. Retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //2. Set post to Comment Entity
        comment.setPost(post);

        //3. Save Comment Entity to the DB
        Comment newComment = commentRepository.save(comment);

        return mapToCommentDto(newComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        // Now convert the list of comments into list of comment dto's
        return comments.stream().map(this::mapToCommentDto).collect(Collectors.toList()); //used method reference for lambda method
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        //1. Retrieve the posts by the postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //2. Retrieve the comment with the comment id provided by the url template
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //3. Custom Exception check exception class
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        //4. Finally return comment dto object
        return mapToCommentDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest) {

        //1. Retrieve the posts by the postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //2. Retrieve the comment with the comment id provided by the url template
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //3. Custom Exception check exception class
        // Here we are checking whether a comment belong to particular post or not
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        //4. Finally if no exceptions occur go ahead and update the comment
        // here I tried constructor instead of using the setter methods of comment fields but it didn't work
        // Comment newComment = new Comment(comment.getName(), comment.getEmail(), comment.getBody());
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        //5. Write this to DB
        Comment updatedComment = commentRepository.save(comment);

        //6. Return Comment dto to the client as response
        return mapToCommentDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        //1. Retrieve the posts by the postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //2. Retrieve the comment with the comment id provided by the url template
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        //3. Custom Exception check exception class
        // Here we are checking whether a comment belong to particular post or not
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        //4. If No exceptions occur go ahead and delete that comment
        commentRepository.delete(comment);


    }

    // Create the Comment Entity to Comment Dto
    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    // Similarly create Comment DTO to Comment Entity
    private Comment mapToCommentEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}
