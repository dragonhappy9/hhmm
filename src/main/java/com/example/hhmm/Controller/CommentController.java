package com.example.hhmm.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; 

import com.example.hhmm.DTO.CommentDTO;
import com.example.hhmm.DTO.PostDTO;
import com.example.hhmm.Service.CommentService;
import com.example.hhmm.Service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/post/comments")
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    // 모든 Comment 불러오기(postId에해당하는)
    @GetMapping("/{postId}")
    public String comments(@PathVariable Long postId, Model model) {
        List<CommentDTO> commentDTOs = commentService.getCommentsByPost(postId);
        model.addAttribute("commentDTOs", commentDTOs);
        return "post/post_detail";
    }
        

    // create 응답
    @PostMapping("/create/{postId}")
    public String createComment(Model model, @PathVariable("postId") Long postId, @Valid CommentDTO commentDTO, BindingResult bindingResult) {
        PostDTO postDTO = this.postService.readPost(postId);
        List<CommentDTO> commentDTOs = commentService.getCommentsByPost(postId);
        if(bindingResult.hasErrors()){
            model.addAttribute("commentDTOs", commentDTOs);
            model.addAttribute("postDTO", postDTO);
            return "post/post_detail";
        }
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
