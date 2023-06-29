package com.rest.BlogPlatformAPI.utils;

import com.rest.BlogPlatformAPI.model.BlogPost;
import com.rest.BlogPlatformAPI.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BlogPostRepository blogPostRepository) {
        return args -> {
            blogPostRepository.save(new BlogPost("title 1", "content 1", "author 1"));
            blogPostRepository.save(new BlogPost("title 2", "content 2", "author 2"));
            blogPostRepository.save(new BlogPost("title 3", "content 3", "author 3"));

            blogPostRepository.findAll().forEach(blogPost -> {
                log.info("Preloaded: " + blogPost);
            });
        };
    }
}
