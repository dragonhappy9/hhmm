package com.example.hhmm.Post;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.Comment.Comment;
import com.example.hhmm.Comment.CommentDTO;
import com.example.hhmm.Item.Item;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    private Specification<Post> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(@NonNull Root<Post> q, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                if (query != null) {
                    query.distinct(true);  // query가 null이 아닐 때만 중복 제거 설정
                }
                Join<Post, Comment> a = q.join("comments", JoinType.LEFT);

                return cb.or(cb.like(q.get("title"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(q.get("nickname"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(a.get("nickname"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }

    // Post 목록 가져오기
    @Transactional(readOnly = true)
    public Page<PostDTO> getPostList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Post> spec = search(kw);
        Page<Post> posts = postRepository.findAll(spec, pageable);
        return posts.map(PostDTO::new);
    }

    // Post 가져오기
    @Transactional(readOnly = true)
    public PostDTO getPost(Long postId){
        Post post = this.postRepository.findByIdWithComments(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found"));

        float post_starpoint = 0;
        int comment_size = post.getComments().size();
        int count = 0;

        if (comment_size > 0) { // 댓글이 있고
            for (int i = 0; i < comment_size; i++) {
                Comment comment = post.getComments().get(i);
                
                if(comment.getStarpoint() != 0) { // 별점이 0인거 빼고
                    post_starpoint += comment.getStarpoint();
                    count++;
                }
            }
    
            if (count > 0) { // count0일때 나누면 NaN
                post_starpoint /= count;
            } else {
                post_starpoint = 0;
            }
        } else {
            post_starpoint = 0;
        }
        post.setStarpoint(post_starpoint);

        PostDTO postDTO = new PostDTO(post);
        postDTO.setCommentDTOs(post.getComments().stream()
                              .map(CommentDTO::new)
                              .collect(Collectors.toList()));
        return postDTO;
    }

    // Post Create
    @Transactional
    public void createPost(PostDTO postDTO){
        Post post = new Post(postDTO);
        this.postRepository.save(post);
    }

    // Post Read
    @Transactional(readOnly = true)
    public PostDTO readPost(Long postId) {
        Post post = this.postRepository.findByIdWithComments(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);  // 조회수 증가

        float post_starpoint = 0;
        int comment_size = post.getComments().size();
        int count = 0;
    
        if (comment_size > 0) { // 댓글이 있고
            for (int i = 0; i < comment_size; i++) {
                Comment comment = post.getComments().get(i);
                
                if(comment.getStarpoint() != 0) { // 별점이 0인거 빼고
                    post_starpoint += comment.getStarpoint();
                    count++;
                }
            }
    
            if (count > 0) { // count0일때 나누면 NaN
                post_starpoint /= count;
            } else {
                post_starpoint = 0;
            }
        } else {
            post_starpoint = 0;  // 댓글이 없으면 별점은 0
        }

        post.setStarpoint(post_starpoint);
        
        PostDTO postDTO = new PostDTO(post);
        postDTO.setCommentDTOs(post.getComments().stream()
                              .map(CommentDTO::new)
                              .collect(Collectors.toList()));
        return postDTO;
    }

    // Post Update
    @Transactional
    public void updatePost(Long postId, PostDTO postDTO){
        Post post = this.postRepository.findById(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found")); 
        Item item = post.getItem();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUpdateDate(LocalDateTime.now());
        item.setItemName(postDTO.getItemDTO().getItemName());
        item.setPrice(postDTO.getItemDTO().getPrice());
        item.setQuantity(postDTO.getItemDTO().getQuantity());
        post.setItem(item);
        this.postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }
}
