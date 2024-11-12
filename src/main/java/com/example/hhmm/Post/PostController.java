package com.example.hhmm.Post;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hhmm.Comment.CommentDTO;
import com.example.hhmm.Customer.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    // Post 전체 불러오기
    @GetMapping
    public String posts(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<PostDTO> postDTOs = this.postService.getPostList(page, kw);
        model.addAttribute("postDTOs", postDTOs);
        model.addAttribute("kw", kw);
        return "post/posts";
    }

    // postId로 Post 검색
    @GetMapping("/{postId}")
    public String read(Model model, @PathVariable("postId") Long postId) {
        PostDTO postDTO = this.postService.readPost(postId);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("commnetDTOs", postDTO.getCommentDTOs());
        model.addAttribute("commentDTO", new CommentDTO());
        return "post/post_detail";
    }

    // Post 생성 폼으로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createForm(PostDTO postDTO) {
        return "post/post_create";
    }
    
    // Post 생성 요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public String createPost(@Valid PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        postDTO.setNickname(userDetails.getNickname());

        postService.createPost(postDTO);
        redirectAttributes.addFlashAttribute("message", "Post create 성공");
        return "redirect:/posts";
    }

    // Post 수정 폼으로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}/edit")
    public String editForm(Model model, @PathVariable("postId") Long postId) {
        PostDTO postDTO = postService.getPost(postId);

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();

        if (!postDTO.getNickname().equals(nickname)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        model.addAttribute("postDTO", postDTO);
        return "post/post_update";
    }

    
    // Post 수정 요청
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}")
    public String editPost(@Valid PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/post_update";
        }
        PostDTO _postDTO = postService.getPost(postDTO.getPostId());

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();

        if(!_postDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        postService.updatePost(postDTO.getPostId(), postDTO);
        redirectAttributes.addFlashAttribute("message", "Post update 성공");
        return "redirect:/posts/" + postDTO.getPostId();
    }

     // Post 삭제 요청
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") Long postId, RedirectAttributes redirectAttributes) {
        PostDTO postDTO = postService.getPost(postId);

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();

        if(!postDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        postService.deletePost(postDTO.getPostId());
        redirectAttributes.addFlashAttribute("message", "Post delete 성공");
        return "redirect:/posts";
    }
}
