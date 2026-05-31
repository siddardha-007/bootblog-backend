package com.siddardha_007.blog_platform.repository;

import com.siddardha_007.blog_platform.model.Comment;
import com.siddardha_007.blog_platform.model.Post;
import com.siddardha_007.blog_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
