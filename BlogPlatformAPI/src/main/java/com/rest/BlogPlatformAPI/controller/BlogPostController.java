package com.rest.BlogPlatformAPI.controller;

import com.rest.BlogPlatformAPI.model.BlogPost;
import com.rest.BlogPlatformAPI.repository.BlogPostRepository;
import com.rest.BlogPlatformAPI.service.BlogPostService;
import com.rest.BlogPlatformAPI.utils.BlogPostModelAssembler;
import com.rest.BlogPlatformAPI.utils.BlogPostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/posts")
public class BlogPostController {
    private final BlogPostService blogPostService;
    private final BlogPostModelAssembler blogPostModelAssembler;

    @Autowired
    public BlogPostController(BlogPostService blogPostService, BlogPostModelAssembler blogPostModelAssembler) {
        this.blogPostService = blogPostService;
        this.blogPostModelAssembler = blogPostModelAssembler;
    }

    //READ
    @GetMapping("")
    public CollectionModel<EntityModel<BlogPost>> getAllPosts() {
        List<EntityModel<BlogPost>> blogPosts = blogPostService.getAllPosts().stream()
                .map(blogPostModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(blogPosts,
                linkTo(methodOn(BlogPostController.class).getAllPosts()).withSelfRel());
    }


    @GetMapping("/{id}")
    public EntityModel<BlogPost> getPostsById(@PathVariable Long id) { //("id")
        BlogPost blogPost = blogPostService.getPostById(id)
                .orElseThrow(() -> new BlogPostNotFoundException(id));

        return blogPostModelAssembler.toModel(blogPost);
    }


//    @PostMapping("")
//    public BlogPost createPost(@RequestBody BlogPost blogPost) {
//        return blogPostService.createPost(blogPost);
//    }
//    @PutMapping("/{id}")
//    public BlogPost updatePost(@PathVariable("id") Long id, @RequestBody BlogPost updatedPost) {
//        return blogPostService.updatePost(id, updatedPost);
//    }
//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable("id") Long id) {
//        blogPostService.deletePost(id);
//    }

    //CREATE
    @PostMapping("")
    public EntityModel<BlogPost> createPost(@RequestBody BlogPost blogPost) {
        BlogPost createdPost = blogPostService.createPost(blogPost);

        return EntityModel.of(createdPost,
                linkTo(methodOn(BlogPostController.class).getPostsById(createdPost.getId())).withSelfRel(),
                linkTo(methodOn(BlogPostController.class).getAllPosts()).withRel("blogPosts"));
    }

    //UPDATE
    @PutMapping("/update/{id}")
    public EntityModel<?> updatePost(@PathVariable Long id, @RequestBody BlogPost updatedPost) {
        BlogPost updated = blogPostService.updatePost(id, updatedPost);

        return EntityModel
                .of(updated,
                linkTo(methodOn(BlogPostController.class).getPostsById(id)).withSelfRel(),
                linkTo(methodOn(BlogPostController.class).getAllPosts()).withRel("blogPosts"));
    }



    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        BlogPost blogPost = blogPostService.getPostById(id)
                .orElseThrow(() -> new BlogPostNotFoundException(id));
        return ResponseEntity
                .ok(blogPostModelAssembler.toModel(blogPostService.createPost(blogPost)))
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You cannot delete this post"));
    }

}


