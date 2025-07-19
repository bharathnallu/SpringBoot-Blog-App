package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // We are not needed to write any methods here.
    // JPARepository already has CRUD repository methods
    // SimpleJpaRepository.java class --> has the implementations for all methods in JPARepository
    // Also @Repository , @Transactional is already internally annotated on top of SimpleJPARepository.java class


}
