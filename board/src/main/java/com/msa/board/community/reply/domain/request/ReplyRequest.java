package com.msa.board.community.reply.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyRequest {
    private Long communityId;
    private String replyContent;
    private String username;
}
