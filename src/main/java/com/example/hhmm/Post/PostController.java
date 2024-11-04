package com.example.hhmm.Post;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hhmm.Comment.CommentDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    // Post 전체 불러오기
    @GetMapping
    public String posts(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<PostDTO> postDTOs = this.postService.getPostList(page);
        model.addAttribute("postDTOs", postDTOs);
        return "post/posts";
    }

    // postId로 Post 검색
    @GetMapping("/{postId}")
    public String read(Model model, @PathVariable("postId") Long postId) {
        PostDTO postDTO = this.postService.readPost(postId);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("commentDTO", new CommentDTO());
        return "post/post_detail";
    }

    // Post 생성 폼으로 이동
    @GetMapping("/create")
    public String createForm(PostDTO postDTO) {
        return "post/post_create";
    }
    
    // Post 생성 요청
    @PostMapping
    public String createPost(@Valid PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }
        postService.createPost(postDTO);
        redirectAttributes.addFlashAttribute("message", "Post create 성공");
        return "redirect:/post/posts";
    }

    // Post 수정 폼으로 이동
    @GetMapping("/{postId}/edit")
    public String editForm(Model model, @PathVariable Long postId) {
        PostDTO postDTO = postService.getPost(postId);
        model.addAttribute("postDTO", postDTO);
        return "post/post_update";
    }

    // Post 수정 요청
    @PatchMapping("/{postId}")
    public String editPost(@PathVariable Long postId, @ModelAttribute PostDTO postDTO, RedirectAttributes redirectAttributes) {
        postService.updatePost(postId, postDTO);
        redirectAttributes.addFlashAttribute("message", "Post update 성공");
        return "redirect:/post/posts";
    }

     // Post 삭제 요청
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId, RedirectAttributes redirectAttributes) {
        postService.deletePost(postId);
        redirectAttributes.addFlashAttribute("message", "Post delete 성공");
        return "redirect:/post/posts";
    }  
}
