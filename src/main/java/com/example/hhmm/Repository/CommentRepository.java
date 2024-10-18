package com.example.hhmm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hhmm.Entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPost_PostId(Long postId);
}
