// package com.example.hhmm;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.example.hhmm.Comment.Comment;
// import com.example.hhmm.Comment.CommentRepository;
// import com.example.hhmm.Item.Item;
// import com.example.hhmm.Item.ItemLog;
// import com.example.hhmm.Item.ItemLogId;
// import com.example.hhmm.Item.ItemLogRepository;
// import com.example.hhmm.Item.ItemRepository;
// import com.example.hhmm.Post.Post;
// import com.example.hhmm.Post.PostDTO;
// import com.example.hhmm.Post.PostRepository;
// import com.example.hhmm.Post.PostService;

// import java.time.LocalDate;
// import java.util.Optional;

// @SpringBootTest
// class RepositoryTests {

//     @Autowired
//     private PostRepository postRepository;
//     @Autowired
//     private CommentRepository commentRepository;
//     @Autowired
//     private PostService postService;
//     @Autowired
//     private ItemRepository itemRepository;
//     @Autowired
//     private ItemLogRepository itemLogRepository;

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
// }