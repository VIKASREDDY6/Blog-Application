package com.blogapp.service.impl;

import com.blogapp.dao.CommentDao;
import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.service.CommentService;
import com.blogapp.service.PostService;
import com.blogapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto getCommentById(int commentId, int postId) {
        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        return commentToDto(comment);
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
        PostDto postDto = postService.getPostById(postId);
        UserDto userDto = userService.getUserById(userId);

        Comment comment = dtoToComment(commentDto);
        comment.setUser(((UserServiceImpl)userService).dtoToUser(userDto));
        comment.setPost(((PostServiceImpl)postService).dtoToPost(postDto));

        Comment createdComment = commentDao.save(comment);

        return commentToDto(createdComment);
    }


    @Override
    public CommentDto updateComment(int commentId, int postId, CommentDto commentDto) {
        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        PostDto postDto = postService.getPostById(postId);
        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentDao.save(comment);

        return commentToDto(updatedComment);
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentDao.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        commentDao.delete(comment);
    }

    public CommentDto commentToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public Comment dtoToComment(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
