package com.example.hhmm.Comment;

public class CommentMapper {
    
    public static Comment toEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setNickname(comment.getNickname());
        comment.setRegDate(commentDTO.getRegDate());
        comment.setUpdateDate(commentDTO.getUpdateDate());
        comment.setStarpoint(commentDTO.getStarpoint());
        return comment;
    }

    public static CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
            comment.getId(),
            comment.getContent(),
            comment.getNickname(),
            comment.getRegDate(),
            comment.getUpdateDate(),
            comment.getStarpoint()
        );
    }
}
