package com.example.hhmm.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hhmm.DTO.PostDTO;
import com.example.hhmm.Entity.Post;
import com.example.hhmm.Service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    // 모델 전체 불러오기
    @GetMapping
    public String posts(Model model) {
        List<Post> posts = this.postService.getPostList();
        model.addAttribute("posts", posts);

        return "posts";
    }

    // postId로 검색
    @GetMapping("/{postId}")
    public String read(Model model, @PathVariable("postId") Long postId) {
        Post post = this.postService.readPost(postId);
        model.addAttribute("post", post);

        return "post";
    }

    // create 요청
    @GetMapping("/create")
    public String create() {
        return "createPost";
    }

    // 리다이렉트를 통해 페이지를 이동하는 것은 좋은데, 
    // 이 경우 내가 수행한 로직(상품 등록, 상품 수정 등) 이 
    // 정상적으로 완료되었는지를 알 수 없다. 
    // 그래서 리다이렉트 된 페이지에 이런 결과를 노출하고싶을 때 
    // RedirectAttributes를 이용하면 된다.
    
    // create 응답
    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDTO postDTO) {
        postService.createPost(postDTO);
        return "redirect:/posts";
    }

    // update 요청
    @GetMapping("/{postId}/update")
    public String update(@PathVariable Long postId, Model model) {
        Post post = postService.getPost(postId);
        model.addAttribute("post", post);

        return "updatePost";
    }

    // update 응답
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostDTO postDTO) {
        postService.updatePost(postId, postDTO);
        return "redirect:/posts";
    }

    // delete 요청
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    // delete 응답
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId, @ModelAttribute PostDTO postDTO) {
        postService.deletePost(postId);
        return "redirect:/posts";
    }
    
}
