package com.blogapp.controller;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogapp")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable("userId") int userId,@PathVariable("categoryId") int categoryId, @RequestBody PostDto postDto) {
        PostDto createdPostDto = postService.createPost(userId, categoryId, postDto);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("userId") int userId, @PathVariable("postId") int postId, @RequestBody PostDto postDto) {
        PostDto updatedPostDto = postService.updatePost(userId, postId, postDto);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("userId") int userId, @PathVariable("postId") int postId) {
        postService.deletePost(userId, postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.ASCENDING, required = false) String sortDirection) {
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") int userId) {
        List<PostDto> postDtoList = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") int categoryId) {
        List<PostDto> postDtoList = postService.getAllPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/search/posts/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keyword") String keyword) {
        List<PostDto> postDtoList = postService.searchPost(keyword);
        return ResponseEntity.ok(postDtoList);
    }
}
