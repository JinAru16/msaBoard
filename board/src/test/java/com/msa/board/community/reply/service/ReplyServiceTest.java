package com.msa.board.community.reply.service;

import com.msa.board.common.exception.BoardException;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.reply.domain.entity.Reply;
import com.msa.board.community.reply.domain.request.ReplyRequest;
import com.msa.board.community.reply.repository.ReplyRepository;
import com.msa.board.community.repository.CommunityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class ReplyServiceTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Test
    void addReply() {
        // given
        ReplyRequest request = new ReplyRequest(1L, "test reply", "test user");

        // when
        Community communityForReply = communityRepository.findById(1L).orElseThrow(() -> new BoardException("Community not found"));

        Reply reply = new Reply();
        Reply newReply = reply.createReply(request, communityForReply);
        Reply save = replyRepository.save(newReply);

        // then
        System.out.println("community_id : " + save.getCommunity().getId());
        System.out.println("reply id : " + save.getId());
        System.out.println("reply content : " + save.getReplyContent());
        assertThat(save.getId()).isNotNull();
        assertThat(save.getReplyContent()).isEqualTo("test reply");

    }
}