package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Custom method to create the List of comments from post Id
    // We need custom method because this is comment entity and standard Jpa can only provide findby methods using commentId
    List<Comment> findByPostId(long postId);

}
