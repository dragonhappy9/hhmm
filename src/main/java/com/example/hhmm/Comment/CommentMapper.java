package com.example.hhmm.Comment;

public class CommentMapper {
    public static CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
            comment.getId(),
            comment.getContent(),
            comment.getStarpoint()
        );
    }
}
