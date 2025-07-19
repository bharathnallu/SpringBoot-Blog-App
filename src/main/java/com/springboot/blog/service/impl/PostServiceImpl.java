package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Implements the PostService interface and contains the business logic for Post operations
// This indicates the class is service class and allows auto detection while component scanning
// we can autowire this class in other classes
@Service
public class PostServiceImpl implements PostService {

    // 2). Since we also need PostRepository methods so we use constructor based dependency injection here.
    private final PostRepository postRepository;

    // Since we only have one constructor we can skip adding @Autowired annotation for spring 4.3 or later versions.
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 1). This is for CreatePost Endpoint
    // Since we are implementing PostService we have to Override the methods from there and implement them here.
    @Override
    public PostDto createPost(PostDto postDto) {

        //        // Convert DTO to entity
        //        // Step1: Create a Post Object and all the DTO details into the `post` object.
        //        Post post = new Post();
        //        // BeanUtils.copyProperties(postDto, post);  // alternatively we can use this method instead of setting manually
        //        post.setTitle(postDto.getTitle());
        //        post.setDescription(postDto.getDescription());
        //        post.setContent(postDto.getContent());

        // USE CUSTOM METHOD DEFINED IN THIS CLASS
        Post post = mapToPostEntity(postDto);

        // Step2: We will call the repository object to save this entity into the database
        Post newPost = postRepository.save(post);   // Here we saved the above created `post` object into the database.
        // This save() method returns a 'newPost' that is the saved post in database

        // Step3: We need to send this saved post details to the REST API using PostDto
        // Let's convert entity into DTO
        //        PostDto postResponse = new PostDto();
        //        // BeanUtils.copyProperties(newPost, newPostDto); // alternatively we can also use this instead of setting manually
        //        postResponse.setId(newPost.getId());
        //        postResponse.setTitle(newPost.getTitle());
        //        postResponse.setDescription(newPost.getDescription());
        //        postResponse.setContent(newPost.getContent());
        //
        //        return postResponse;
        //    }

        // USE CUSTOM METHOD DEFINED IN THIS CLASS for converting post entity to post DTO
        PostDto postResponse = mapToPostDto(newPost);
        return postResponse;
    }

    // 2). This is for getAllPosts() Endpoint
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // For sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //1. To enable pagination create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //2. Update the findAll Method for pagination
//        List<Post> posts = postRepository.findAll();
        Page<Post> posts = postRepository.findAll(pageable);    // The return type is Page for pagination so changed from List to Page

        //3. get content for the page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content =  listOfPosts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());


        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        postResponse.setFirst(posts.isFirst());

        return postResponse;
    }

    // Convert Entity into a DTO is reused here. so create a common private method
    private PostDto mapToPostDto(Post post) {
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        return postDto;
    }

    // Convert DTO into Entity is reused here. So create a common private method for that as well
    private Post mapToPostEntity(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }

    // 3). This is for getPostById() Endpoint
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToPostDto(post);
    }

    // 4). This is for updatePost() Endpoint
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // get hold of post by id from the database if the id is not found throw an exception
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Next copy the contents of postDto into the new Post object
        //BeanUtils.copyProperties(postDto, post); // This is setting null breaking because of this
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        // save the new Post object to the database
        Post updatedPost = postRepository.save(post);

        return mapToPostDto(updatedPost);
    }

    // 5). This is for DeletePost() Endpoint

    @Override
    public void deletePostById(Long id) {
        // get hold of post by id from the database if the id is not found throw an exception
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);

    }
}
