package com.blogapp.service.impl;

import com.blogapp.dao.PostDao;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.service.CategoryService;
import com.blogapp.service.PostService;
import com.blogapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PostDto getPostById(int postId) {
        Post post = postDao.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        return postToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")) sort = Sort.by(sortBy).ascending();
        else sort = Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = postDao.findAll(pageable);
        PostResponse postResponse = new PostResponse();
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post: postPage) {
            postDtoList.add(postToDto(post));
        }
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPostsOfUser(int userId) {
        UserDto userDto = userService.getUserById(userId);
        List<Post> postList = postDao.findByUser(((UserServiceImpl)userService).dtoToUser(userDto));
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post: postList) {
            postDtoList.add(postToDto(post));
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> getAllPostsByCategory(int categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        List<Post> postList = postDao.findByCategory(((CategoryServiceImpl)categoryService).dtoToCategory(categoryDto));
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post: postList) {
            postDtoList.add(postToDto(post));
        }
        return postDtoList;
    }

    @Override
    public PostDto createPost(int userId, int categoryId, PostDto postDto) {
        Post post = dtoToPost(postDto);
        UserDto userDto = userService.getUserById(userId);
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        post.setUser(((UserServiceImpl)userService).dtoToUser(userDto));
        post.setCategory(((CategoryServiceImpl)categoryService).dtoToCategory(categoryDto));
        post.setDate(new Date());

        Post createdPost = postDao.save(post);
        return postToDto(createdPost);
    }

    @Override
    public PostDto updatePost(int userId, int postId, PostDto postDto) {
        Post post = postDao.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDate(new Date());
        Post updatedPost = postDao.save(post);
        return postToDto(updatedPost);
    }

    @Override
    public void deletePost(int userId, int postId) {
        Post post = postDao.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        UserDto userDto = userService.getUserById(userId);
        postDao.delete(post);
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> postList = postDao.findByTitleContaining(keyword);
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post: postList) {
            postDtoList.add(postToDto(post));
        }
        return postDtoList;
    }

    public PostDto postToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public Post dtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
