package com.rest.BlogPlatformAPI.controller;

import com.rest.BlogPlatformAPI.model.BlogPost;
import com.rest.BlogPlatformAPI.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view")
public class BlogPostThymeController {

    private final BlogPostService blogPostService;
    @Autowired
    BlogPostThymeController(BlogPostService blogPostService) {
        this.blogPostService =blogPostService;
    }

    @GetMapping("")
    public String getAllPosts(Model model) {
        List<BlogPost> blogPostList = blogPostService.getAllPosts();
        model.addAttribute("blogPosts", blogPostList);
        return "postList.html";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        Optional<BlogPost> blogPost = blogPostService.getPostById(id);
        if(blogPost.isPresent()) {
            model.addAttribute("blogPost", blogPost.get());
            return "post.html";
        } else {
            return "error.html";
        }
    }

    @GetMapping("/create")
    public String createPostForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "createForm.html";
    }

    @PostMapping("")
    public String createPost(@ModelAttribute("blogPost") BlogPost blogPost) {
        blogPostService.createPost(blogPost);
        return "redirect:/view";
    }

    @GetMapping("/update/{id}")
    public String updatePostForm(@PathVariable("id") Long id, Model model) {

        Optional<BlogPost> blogPost = blogPostService.getPostById(id);
        if (blogPost.isPresent()) {
            model.addAttribute("blogPost", blogPost.get());
            return "updateForm.html";
        } else {
            return "error.html";
        }
    }

    @PostMapping("/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("blogPost") BlogPost updatedBlogPost) {
        Optional<BlogPost> existingPost = blogPostService.getPostById(id);
        if (existingPost == null) {
            return "error.html";
        }
        blogPostService.updatePost(id, updatedBlogPost);
        return "redirect:/view";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        blogPostService.deletePost(id);
        return "redirect:/view";
    }

}
