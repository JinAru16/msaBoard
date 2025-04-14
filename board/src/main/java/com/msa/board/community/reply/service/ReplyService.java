package com.msa.board.community.reply.service;

import com.msa.board.common.exception.BoardException;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.reply.domain.entity.Reply;
import com.msa.board.community.reply.domain.request.ReplyRequest;
import com.msa.board.community.reply.domain.response.ReplyResponse;
import com.msa.board.community.reply.repository.ReplyRepository;
import com.msa.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final CommunityRepository communityRepository;
    private final ReplyRepository replyRepository;

    public List<ReplyResponse> findAllReply(Long id) {
       return replyRepository.findAllReplyByCommunityId(id).stream().map(ReplyResponse::new).collect(Collectors.toList());
    }

    public ReplyResponse addReply(ReplyRequest replyRequest) {
        Reply reply = new Reply();
        Optional<Community> community = communityRepository.findById(replyRequest.getCommunityId());
       if (community.isPresent()) {
           Reply createdReply = reply.createReply(replyRequest, community.get());
           replyRepository.save(createdReply);
           return new ReplyResponse(createdReply);
       }else {
           throw new BoardException("해당 게시글이 올바르지않습니다.");
       }
    }
}
