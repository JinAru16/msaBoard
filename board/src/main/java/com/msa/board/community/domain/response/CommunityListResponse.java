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
public class CommunityListResponse {
    private Long id;
    private String username;
    private String title;
    private LocalDateTime updateTime;

    public CommunityListResponse(Community community) {
        this.id = community.getId();
        this.username = community.getUsername();
        this.title = community.getTitle();
        this.updateTime = community.getUpdateTime();
    }
}
