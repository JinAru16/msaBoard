package com.msa.board.community.domain.response;

import com.msa.board.community.domain.Entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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
