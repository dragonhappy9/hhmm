package com.example.hhmm.Post;

import com.example.hhmm.Item.ItemMapper;

public class PostMapper {

    public static Post toEntity(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setItemDescript(postDTO.getItemDescript());
        post.setName(postDTO.getName());
        post.setRegDate(postDTO.getRegDate());
        post.setUpdateDate(postDTO.getUpdateDate());
        post.setViewCount(postDTO.getViewCount());
        post.setStarpoint(postDTO.getStarpoint());
        post.setItem(ItemMapper.toEntity(postDTO.getItemDTO()));
        return post;
    }

    public static PostDTO toDTO(Post post) {
        return new PostDTO(
            post.getId(),
            post.getTitle(),
            post.getItemDescript(),
            post.getName(),
            post.getRegDate(),
            post.getUpdateDate(),
            post.getViewCount(),
            post.getStarpoint(),
            ItemMapper.toDTO(post.getItem())
        );
    }
}
