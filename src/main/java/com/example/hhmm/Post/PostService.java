package com.example.hhmm.Post;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.Comment.Comment;
import com.example.hhmm.Item.Item;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

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
                    query.distinct(true); // query가 null이 아닐 때만 중복 제거 설정
                }
                Join<Post, Comment> a = q.join("comments", JoinType.LEFT);
                Join<Post, Item> b = q.join("item", JoinType.LEFT);
                
                String keyword = "%" + kw.trim() + "%";
                return cb.or(
                        cb.like(q.get("title"), keyword), // 제목
                        cb.like(q.get("itemDescript"), keyword), // 상품설명
                        cb.like(q.get("name"), keyword), // 게시글 작성자 
                        cb.like(a.get("content"), keyword), // 후기 내용 
                        cb.like(a.get("nickname"), keyword), // 후기 작성자 별명
                        cb.like(b.get("itemName"), keyword)); // Item 이름
            }
        };
    }

    // Post 목록 가져오기
    @Transactional(readOnly = true)
    public Page<PostDTO> getPostList(int page, String kw) { // 요청한 페이지번호와 검색어
        List<Sort.Order> sorts = new ArrayList<>(); // 정렬기준
        sorts.add(Sort.Order.desc("regDate")); // 등록일수 기준 정렬
        Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts)); // 페이징조건
        Specification<Post> spec = search(kw); // 검색조건
        Page<Post> posts = postRepository.findAll(spec, pageable); // 검색조건, 페이징조건에 따라 Post검색
        return posts.map(PostMapper::toDTO); // DTO로 변환하여 반환
    }

    // Post 가져오기
    @Transactional(readOnly = true)
    public PostDTO getPost(Long postId, boolean viewCountUp){
        Post post = this.postRepository.findByIdWithComments(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found"));
        float postStarPoint = 0;
        int count = 0;
        int commentSize = post.getComments().size();

        if (commentSize > 0) { // 댓글이 있고
            for (int i = 0; i < commentSize ; i++) {
                Comment comment = post.getComments().get(i);

                if(comment.getStarpoint() != 0) { // 별점이 0이 아닌 후기만 집계
                    postStarPoint += comment.getStarpoint();
                    count++;
                }
            }
            postStarPoint = (count > 0) ? (postStarPoint / count) : 0; // count가 0일때 나누면 NaN에러
        } else {
            postStarPoint = 0;  // 댓글이 없으면 별점은 0
        }

        if (viewCountUp) { // 조회수 증가를 필요시에만
            post.setViewCount(post.getViewCount() + 1);
        }

        post.setStarpoint(postStarPoint);
        return PostMapper.toDTO(post);
    }

    // Post 생성
    @Transactional
    public void createPost(PostDTO postDTO){
        Post post = PostMapper.toEntity(postDTO);
        this.postRepository.save(post);
    }
    

    // Post 업데이트
    // 입력받을 값 : title, itemDescript, Item
    @Transactional
    public void updatePost(Long postId, PostDTO postDTO){
        Post post = this.postRepository.findById(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found")); 
        post = PostMapper.toEntity(postDTO);
        post.setUpdateDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    // Post 삭제
    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }
}
