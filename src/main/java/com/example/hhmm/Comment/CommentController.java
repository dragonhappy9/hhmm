package com.example.hhmm.Comment;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String createComment(
        Model model, 
        @PathVariable("postId") Long postId, 
        @Valid CommentDTO commentDTO, 
        BindingResult bindingResult, 
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal CustomUserDetails userDetails 
    ){
        boolean viewCountUp = false;
        PostDTO postDTO = this.postService.getPost(postId, viewCountUp);

        // Valid(유효성 검증)을 수행하고 통과하지 못하면 통과하지 못한 부분을 알려줌 
        if(bindingResult.hasErrors()){  
            model.addAttribute("postDTO", postDTO);
            model.addAttribute("commentDTO", commentDTO);
            return "post/post_detail";
        }

        // @AuthenticationPrincipal로 매개변수를 통해 쉽게 userDetails를 가져올수도 있음!
        String nickname = userDetails.getNickname();
        commentDTO.setNickname(nickname);
        commentDTO = commentService.createComment(postId, commentDTO);
        
        redirectAttributes.addFlashAttribute("message", "Comment create 성공");

        // 앵커를 적용하여 자신이 작성한 후기로 리다이렉트 시킨다.
        return "redirect:/posts/" + postId + "#commentDTO_" + commentDTO.getId();
    }
    
    // Comment 수정 요청
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}/comments/{commentId}")
    public String editComment(
        @Valid CommentDTO commentDTO, 
        BindingResult bindingResult, 
        RedirectAttributes redirectAttributes, 
        @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        if (bindingResult.hasErrors()) {
            return "post/post_detail";
        }

        CommentDTO _commentDTO = commentService.getComment(commentDTO.getId()); 
        String nickname = userDetails.getNickname();
        if(!_commentDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        commentService.updateComment(commentDTO.getId(), commentDTO);
        redirectAttributes.addFlashAttribute("message", "Comment update 성공");
        return "redirect:/posts/" + commentDTO.getId();
    }

    // deleteComment 요청
    @DeleteMapping("/{postId}/comments/{commentId}")
    public String deleteComment(
        CommentDTO commentDTO, 
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal CustomUserDetails userDetails    
    ) {
        CommentDTO _commentDTO = commentService.getComment(commentDTO.getId());
        String nickname = userDetails.getNickname();
        if(!_commentDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        commentService.deleteComment(commentDTO.getId());
        redirectAttributes.addFlashAttribute("message", "Comment delete 성공");
        return "redirect:/posts/" + commentDTO.getId();
    }
}
