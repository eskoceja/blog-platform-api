package com.rest.BlogPlatformAPI.repository;

import com.rest.BlogPlatformAPI.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
