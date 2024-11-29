package com.example.hhmm.Post;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.multipart.MultipartFile;
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
    public String read(Model model, @PathVariable("postId") Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostDTO postDTO = this.postService.readPost(postId);
        if (userDetails != null) {
            model.addAttribute("customerNickname", userDetails.getNickname());
        }
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("ItemDTO", postDTO.getItemDTO());
        model.addAttribute("commnetDTOs", postDTO.getCommentDTOs());
        model.addAttribute("commentDTO", new CommentDTO());
        return "post/post_detail";
    }

    // 파일 전송
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = "src/main/resources/static/file_path/" + file.getOriginalFilename();
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return ResponseEntity.ok(file.getOriginalFilename()); // 파일이 존재하면 같은 파일로 보고 경로만 반환
            }                                       // 나중에 시간이 남으면 파일 저장시 키값으로 고유한 파일이름으로 저장하도록
                                                    // 실제 파일명은 따로 저장하도록 할 예정
            Files.write(path, file.getBytes());
            return ResponseEntity.ok(file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    // Post 생성 폼으로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createForm(Model model, PostDTO postDTO) {
        return "post/post_create";
    }
    
    // Post 생성 요청
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public String createPost(Model model, @Valid PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "post/post_create";
        }
        postDTO.setNickname(userDetails.getNickname());
        postService.createPost(postDTO);
        redirectAttributes.addFlashAttribute("message", "Post create 성공");
        return "redirect:/posts";
    }

    // Post 수정 폼으로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}/edit")
    public String editForm(Model model, @PathVariable("postId") Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostDTO postDTO = postService.getPost(postId);
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
    public String editPost(PostDTO postDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostDTO _postDTO = postService.getPost(postDTO.getPostId());
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
    public String deletePost(@PathVariable("postId") Long postId, RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostDTO postDTO = postService.getPost(postId);
        String nickname = userDetails.getNickname();
        if(!postDTO.getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        postService.deletePost(postDTO.getPostId());
        redirectAttributes.addFlashAttribute("message", "Post delete 성공");
        return "redirect:/posts";
    }
}
