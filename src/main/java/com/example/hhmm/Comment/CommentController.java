package com.example.hhmm.Comment;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hhmm.Customer.CustomUserDetails;
import com.example.hhmm.Post.PostDTO;
import com.example.hhmm.Post.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    // Comment 생성요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/comments")
    public String createComment(Model model, @PathVariable("postId") Long postId, @Valid CommentDTO commentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        PostDTO postDTO = this.postService.getPost(postId);
        if(bindingResult.hasErrors()){
            model.addAttribute("postDTO", postDTO);
            model.addAttribute("commentDTO", commentDTO);
            return "post/post_detail";
        }

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();

        commentDTO.setNickname(nickname);
        commentService.createComment(postId, commentDTO);
        redirectAttributes.addFlashAttribute("message", "Comment create 성공");
        return "redirect:/posts/" + postId;
    }
    
    // Post 수정 요청
    @PreAuthorize("isAuthenticated()")
    // updateComment 요청
    @PatchMapping("/{postId}/comments/{commentId}")
    public String editComment(@Valid CommentDTO commentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "post/post_detail";
        }
        CommentDTO _commentDTO = commentService.getComment(commentDTO.getCommentId()); 

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();

        if(!_commentDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        commentService.updateComment(commentDTO.getCommentId(), commentDTO);
        redirectAttributes.addFlashAttribute("message", "Comment update 성공");
        return "redirect:/posts/" + commentDTO.getPostId();
    }

    // deleteComment 요청
    @DeleteMapping("/{postId}/comments/{commentId}")
    public String deleteComment(CommentDTO commentDTO, RedirectAttributes redirectAttributes) {
        CommentDTO _commentDTO = commentService.getComment(commentDTO.getCommentId()); 

        // Authentication 객체에서 CustomUserDetails를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();

        if(!_commentDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        commentService.deleteComment(commentDTO.getCommentId());
        redirectAttributes.addFlashAttribute("message", "Comment delete 성공");
        return "redirect:/posts/" + commentDTO.getPostId();
    }
}
