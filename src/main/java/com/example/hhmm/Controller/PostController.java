package com.example.hhmm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import com.example.hhmm.DTO.PostDTO;
import com.example.hhmm.DTO.CommentDTO;
import com.example.hhmm.Service.CommentService;
import com.example.hhmm.Service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    private final CommentService commentService;

    // 모델 전체 불러오기
    @GetMapping("/posts")
    public String posts(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<PostDTO> postDTOs = this.postService.getPostList(page);
        model.addAttribute("posts", postDTOs);
        return "post/posts";
    }

    // postId로 검색
    @GetMapping("/{postId}")
    public String read(@PathVariable("postId") Long postId, Model model) {
        PostDTO postDTO = this.postService.readPost(postId);
        List<CommentDTO> commentDTOs = this.commentService.getCommentsByPost(postId);
        model.addAttribute("post", postDTO);
        model.addAttribute("comments", commentDTOs);
        return "post/post_detail";
    }

    // create 요청페이지
    @GetMapping("/create")
    public String create() {
        return "post/post_create";
    }

    // 리다이렉트를 통해 페이지를 이동하는 것은 좋은데, 
    // 이 경우 내가 수행한 로직(상품 등록, 상품 수정 등) 이 
    // 정상적으로 완료되었는지를 알 수 없다. 
    // 그래서 리다이렉트 된 페이지에 이런 결과를 노출하고싶을 때 
    // RedirectAttributes를 이용하면 된다.
    
    // create 응답
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDTO postDTO, RedirectAttributes redirectAttributes) {
        postService.createPost(postDTO);
        redirectAttributes.addFlashAttribute("message", "Post create 성공");
        return "redirect:/post/posts";
    }

    // update 요청페이지
    @GetMapping("/update/{postId}")
    public String update(@PathVariable Long postId, Model model) {
        PostDTO postDTO = postService.getPost(postId);
        model.addAttribute("post", postDTO);
        return "post/post_update";
    }

    // update 응답
    @PostMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostDTO postDTO, RedirectAttributes redirectAttributes) {
        postService.updatePost(postId, postDTO);
        redirectAttributes.addFlashAttribute("message", "Post update 성공");
        return "redirect:/post/posts";
    }

    // delete 응답
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId, RedirectAttributes redirectAttributes) {
        postService.deletePost(postId);
        redirectAttributes.addFlashAttribute("message", "Post delete 성공");
        return "redirect:/post/posts";
    }
    
}
