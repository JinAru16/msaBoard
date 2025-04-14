package com.msa.board.community.domain.response;

import com.msa.board.community.reply.domain.response.ReplyResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class BoardResponse {
    private final CommunityResponse communityResponse;
    private final List<ReplyResponse> replyResponseList;

    public BoardResponse(CommunityResponse communityResponse, List<ReplyResponse> replyResponseList) {
        this.communityResponse = communityResponse;
        this.replyResponseList = replyResponseList;
    }
}
