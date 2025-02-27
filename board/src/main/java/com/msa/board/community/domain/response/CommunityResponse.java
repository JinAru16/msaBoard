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
public class CommunityResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updateTime;

    public CommunityResponse(Community community) {
        this.id = community.getId();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.updateTime = community.getUpdateTime();
    }
}
