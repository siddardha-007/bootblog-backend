package com.siddardha_007.blog_platform.repository;

import com.siddardha_007.blog_platform.model.Category;
import com.siddardha_007.blog_platform.model.Post;
import com.siddardha_007.blog_platform.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByUser(User user);

    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
