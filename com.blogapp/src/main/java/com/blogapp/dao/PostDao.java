package com.blogapp.dao;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Integer> {
    public List<Post> findByUser(User user);

    public List<Post> findByCategory(Category category);

    public List<Post> findByTitleContaining(String keyword);
}
