package com.rest.BlogPlatformAPI.utils;

import com.rest.BlogPlatformAPI.controller.BlogPostController;
import com.rest.BlogPlatformAPI.model.BlogPost;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BlogPostModelAssembler implements RepresentationModelAssembler<BlogPost, EntityModel<BlogPost>> {
    @Override
    public EntityModel<BlogPost> toModel(BlogPost blogPost) {
        return EntityModel.of(blogPost,
                linkTo(methodOn(BlogPostController.class).getPostsById(blogPost.getId())).withSelfRel(),
                linkTo(methodOn(BlogPostController.class).getAllPosts()).withRel("blog-posts"));
//                linkTo(methodOn(BlogPostController.class).createPost(blogPost)).withRel("create"),
//                linkTo(methodOn(BlogPostController.class).updatePost(blogPost.getId(), blogPost)).withRel("update"),
//                linkTo(methodOn(BlogPostController.class).deletePost(blogPost.getId())).withRel("delete")

    }
}
