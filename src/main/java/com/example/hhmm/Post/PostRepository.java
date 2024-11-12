package com.example.hhmm.Post;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository extends JpaRepository<Post, Long> {

    @NonNull
    Page<Post> findAll(@NonNull Pageable pageable);

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.postId = :postId")
    Optional<Post> findByIdWithComments(@Param("postId") Long postId);

    
}
