package com.example.hhmm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.Bucket.Bucket;
import com.example.hhmm.Comment.Comment;
import com.example.hhmm.Comment.CommentRepository;
import com.example.hhmm.Customer.*;
import com.example.hhmm.Item.Item;
import com.example.hhmm.Item.ItemRepository;
import com.example.hhmm.ItemLog.ItemLogRepository;
import com.example.hhmm.Post.Post;
import com.example.hhmm.Post.PostDTO;
import com.example.hhmm.Post.PostMapper;
import com.example.hhmm.Post.PostRepository;
import com.example.hhmm.Post.PostService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

// import java.time.LocalDate;
// import java.util.Optional;

@SpringBootTest
class RepositoryTests {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemLogRepository itemLogRepository;
    @Autowired
    private CustomerRepository customerRepository;

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

    @Test
    void newCustomerJPA(){
        for(int i = 1; i <= 1000; i++){
            Customer customer = new Customer();
            Bucket bucket = new Bucket();
            customer.setCId(String.valueOf(i));
            customer.setCPw("testpswd"+i);
            customer.setName("testname"+i);
            customer.setNickname("testninm"+i);
            customer.setHome("testhome"+i);
            customer.setGender(true);
            customer.setPayMoney(i*1000);
            customer.setBucket(bucket);
            this.customerRepository.save(customer);
        }
    }

    @Test
    void newPostJPA(){
        for(int i = 1; i <= 1000; i++){
            Post post = new Post();
            Item item = new Item("testItem", i, 1000, "testFile", Long.valueOf(i));
            post.setTitle("test" + i + "번 Post");
            post.setItemDescript("<ul><br><li>test설명</li></ul>");
            post.setName("tester: 즐거운용");
            post.setItem(item);
            this.postRepository.save(post);
        }
    }

    @Test
    void newCommentJPA(){
        for(int i = 1; i <= 1000; i++){
            for(int j = 1; j <= 30; j++){
                float random = (float) (Math.random() * 5);
                Comment comment = new Comment(
                    "test"+ i + "번 Post" + j +"번 Comment", 
                    "tester: 즐거운용", 
                    random, 
                    Long.valueOf(i)
                );
                this.commentRepository.save(comment);
            }
        }
    }

    @Test
    Page<Post> getPostList() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(1, 9, Sort.by(sorts));
        Specification<Post> spec = search("test"); // 검색조건
        Page<Post> posts = postRepository.findAll(spec, pageable);
        assertNotNull(posts);
        assertFalse(posts.isEmpty(), "상품이 존재해야 합니다.");
        return posts;
    }

    @Test
    Post getPost(){
        Optional<Post> post = this.postRepository.findById(1L);
        if(!post.isPresent()){
            new DataNotFoundException("post 없음");
        }else{
            List<Comment> comments = post.get().getComments();
            assertNotNull(comments);
            assertFalse(comments.isEmpty(), "후기가 존재해야 합니다.");
        }
        return post.get();
    }

    // @Test
    // void saveOrUpdateItemLogJPA(){
    //     Optional<Item> item = itemRepository.findById(32L);
    //     if(item.isPresent()){
    //         ItemLog itemLog = new ItemLog();
    //         itemLog.setItem(item.get());
    //         itemLog.setSoldDate(LocalDate.now());
    //         itemLog.setSoldQuantity(20);
    //         this.itemLogRepository.save(itemLog);
    //     }
    // }
    
    // @Test
    // void createTestJPA(){
    //     Post post = new Post();
    //     post.setTitle("테스트 1번");
    //     post.setContent("테스트 1번");
    //     post.setNickname("홍길동");
    //     this.postRepository.save(post);
    // }

    // @Test
    // void create300PostJPA(){
    //     for(int i = 2; i <=300; i++){
    //         PostDTO postDTO = new PostDTO();
    //         postDTO.setTitle(String.format("테스트 데이터 입니다:[%03d]", i));
    //         postDTO.setContent("내용 없음!");
    //         postDTO.setNickname("홍길동");
    //         this.postService.createPost(postDTO);
    //     }
    // }

    // @Test
    // void testJPA2(){
    //     Optional<Post> post = this.postRepository.findById(1L);
    //     if(post.isPresent()){
    //         Comment comment = new Comment();
    //         comment.setContent("호잇");
    //         comment.setNickname("김용희");
    //         comment.setPostId(post.get().getPostId());
    //         this.commentRepository.save(comment);
    //     }
    // }
}