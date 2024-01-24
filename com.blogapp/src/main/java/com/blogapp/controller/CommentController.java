package com.blogapp.controller;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CommentDto;
import com.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogapp")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") int postId, @PathVariable("userId") int userId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto createdCommentDto = commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deteleComment(@PathVariable("commentid") int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("Comment successfully deleted", true));
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateComment(commentId, postId, commentDto);
        return ResponseEntity.ok(updatedCommentDto);
    }
}
