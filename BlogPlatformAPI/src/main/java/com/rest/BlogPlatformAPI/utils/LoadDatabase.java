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
            blogPostRepository.save(new BlogPost("The Art Of Cooking", "Content about the art of cooking.", "John Smith"));
            blogPostRepository.save(new BlogPost("Exploring The Wonders Of Nature", "Content about the wonders of nature.", "Emily Johnson"));
            blogPostRepository.save(new BlogPost("The Power Of Mindfulness", "Content about mindfulness.", "Sarah Thompson"));

            blogPostRepository.findAll().forEach(blogPost -> {
                log.info("Preloaded: " + blogPost);
            });
        };
    }
}
