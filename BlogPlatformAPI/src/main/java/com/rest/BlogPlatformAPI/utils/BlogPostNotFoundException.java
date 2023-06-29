package com.rest.BlogPlatformAPI.utils;

public class BlogPostNotFoundException extends RuntimeException{
    public BlogPostNotFoundException(Long id) {
        super("Blog Post not found by id: " + id);
    }
}
