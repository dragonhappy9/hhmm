package com.example.hhmm.Post;

import com.example.hhmm.Item.ItemMapper;

public class PostMapper {
    public static PostDTO toDTO(Post post) {
        return new PostDTO(
            post.getId(),
            post.getTitle(),
            post.getItemDescript(),
            post.getName(),
            post.getViewCount(),
            post.getStarpoint(),
            ItemMapper.toDTO(post.getItem())
        );
    }
}
