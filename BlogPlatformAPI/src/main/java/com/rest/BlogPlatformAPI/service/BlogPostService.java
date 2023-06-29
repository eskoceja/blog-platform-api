package com.rest.BlogPlatformAPI.service;

import com.rest.BlogPlatformAPI.model.BlogPost;
import com.rest.BlogPlatformAPI.repository.BlogPostRepository;
import com.rest.BlogPlatformAPI.utils.BlogPostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;

    @Autowired
    BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    //READ
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    //CREATE
    public BlogPost createPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    //UPDATE
    public BlogPost updatePost(Long id, BlogPost updatedPost) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost blogPost = post.get();
            blogPost.setTitle(updatedPost.getTitle());
            blogPost.setContent(updatedPost.getContent());
            blogPost.setAuthor(updatedPost.getAuthor());
            return blogPostRepository.save(blogPost);
        } else {
            throw new BlogPostNotFoundException(id);
        }
    }

    //DELETE
    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }
}
