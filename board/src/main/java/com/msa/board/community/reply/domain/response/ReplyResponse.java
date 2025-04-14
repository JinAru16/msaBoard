package com.msa.board.community.reply.domain.response;

import com.msa.board.community.reply.domain.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyResponse {
    private Long communityId;
    private String replyContent;
    private String username;
    private LocalDateTime replyTime;

    public ReplyResponse(Reply reply) {
        this.communityId = reply.getCommunity().getId();
        this.replyContent = reply.getReplyContent();
        this.username = reply.getUsername();
        this.replyTime = reply.getReplyTime();
    }
}
