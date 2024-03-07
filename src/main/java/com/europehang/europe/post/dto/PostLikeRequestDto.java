package com.europehang.europe.post.dto;

import com.europehang.europe.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostLikeRequestDto {
    public User user;
    public Long postId;

}
