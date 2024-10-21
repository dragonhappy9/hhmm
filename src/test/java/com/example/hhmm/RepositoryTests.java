package com.example.hhmm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.hhmm.Entity.Comment;
import com.example.hhmm.Entity.Post;
import com.example.hhmm.Repository.CommentRepository;
import com.example.hhmm.Repository.PostRepository;

import java.util.Optional;

@SpringBootTest
class RepositoryTests {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    
    @Test
    void testJPA(){
        Post post = new Post();
        post.setTitle("테스트 1번");
        post.setContent("테스트 1번");
        post.setNickname("홍길동");
        this.postRepository.save(post);
    }

    @Test
    void testJPA2(){
        Optional<Post> post = this.postRepository.findById(1L);
        if(post.isPresent()){
            Comment comment = new Comment();
            comment.setContent("호잇");
            comment.setNickname("김용희");
            comment.setPostId(post.get().getPostId());
            this.commentRepository.save(comment);
        }
    }

}