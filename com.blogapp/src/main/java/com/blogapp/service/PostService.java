package com.blogapp.service;

import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

import java.util.List;

public interface PostService {
    public PostDto getPostById(int postId);
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection);
    public List<PostDto> getAllPostsOfUser(int userId);
    public List<PostDto> getAllPostsByCategory(int categoryId);
    public PostDto createPost(int userId, int categoryId, PostDto postDto);
    public PostDto updatePost(int userId, int postId, PostDto postDto);
    public void deletePost(int userId, int postId);
    public List<PostDto> searchPost(String keyword);
}
