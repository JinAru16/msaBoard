package com.msa.board.community.domain.response;

import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.reply.domain.response.ReplyResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommunityResponse {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime updateTime;

    public CommunityResponse(Community community) {
        this.id = community.getId();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.username = community.getUsername();
        this.updateTime = community.getUpdateTime();
    }
}
