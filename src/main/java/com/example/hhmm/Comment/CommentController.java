package com.example.hhmm.Comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // createComment 요청
    @PostMapping("/{postId}/comments")
    public String createComment(Model model, @PathVariable("postId") Long postId, @Valid CommentDTO commentDTO, BindingResult bindingResult) {
        PostDTO postDTO = this.postService.readPost(postId);
        if(bindingResult.hasErrors()){
            model.addAttribute("postDTO", postDTO);
            model.addAttribute("commentDTO", commentDTO);
            return "post/post_detail";
        }
        commentService.createComment(postId, commentDTO);
        return "redirect:/posts/" + postId;
    }
    
    // updateComment 요청
    @PatchMapping("/{postId}/comments/{commentId}")
    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, @ModelAttribute CommentDTO commentDTO) {
        commentService.updateComment(commentId, commentDTO);
        return "redirect:/posts/" + postId;
    }

    // deleteComment 요청
    @DeleteMapping("/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }
}
