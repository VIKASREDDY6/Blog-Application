package com.blogapp.service;

import com.blogapp.entities.Comment;
import com.blogapp.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto getCommentById(int commentId, int postId);

    public CommentDto createComment(CommentDto commentDto, int postId, int userId);

    public CommentDto updateComment(int commentId, int postId, CommentDto commentDto);
    public void deleteComment(int commentId);
}
