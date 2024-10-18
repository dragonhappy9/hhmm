package com.example.hhmm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hhmm.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
