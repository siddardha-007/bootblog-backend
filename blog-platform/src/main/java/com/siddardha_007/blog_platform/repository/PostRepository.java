package com.siddardha_007.blog_platform.repository;

import com.siddardha_007.blog_platform.model.Category;
import com.siddardha_007.blog_platform.model.Post;
import com.siddardha_007.blog_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);

    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
