package com.example.hhmm.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; 

import com.example.hhmm.DTO.CommentDTO;
import com.example.hhmm.Service.CommentService;

import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/post/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 모든 Comment 불러오기(postId에해당하는)
    @GetMapping("/{postId}")
    public String comments(@PathVariable Long postId, Model model) {
        List<CommentDTO> commentDTO = commentService.getCommentsByPost(postId);
        model.addAttribute("comments", commentDTO);
        return "post/post_detail";
    }
        

    // create 응답
    @PostMapping("/create/{postId}")
    public String createComment(@PathVariable("postId") Long postId, @ModelAttribute CommentDTO commentDTO) {
        commentService.createComment(postId, commentDTO);
        return "redirect:/post/posts/{postId}";
    }
    
    // update 응답
    @PostMapping("/update/{commentId}")
    public String updatePost(@PathVariable Long commentId, @ModelAttribute CommentDTO commentDTO) {
        commentService.updateComment(commentId, commentDTO);
        return "redirect:/post/posts/{postId}";
    }

    // delete 응답
    @PostMapping("/delete/{commentId}")
    public String deletePost(@PathVariable Long commentId, @ModelAttribute CommentDTO commentDTO) {
        commentService.deleteComment(commentId);
        return "redirect:/post/posts/{postId}";
    }
}
