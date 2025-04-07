package com.msa.board.community.reply.service;

import com.msa.board.common.exception.BoardException;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.reply.domain.entity.Reply;
import com.msa.board.community.reply.domain.request.ReplyRequest;
import com.msa.board.community.reply.repository.ReplyRepository;
import com.msa.board.community.repository.CommunityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReplyServiceTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    CommunityRepository communityRepository;

    @BeforeEach
    void setUp() {
        // id가 3인 게시글에 댓글을 미리 셋팅함.
        Community communityForReply = communityRepository.findById(3L).orElseThrow(() -> new BoardException("Community not found"));
        // given
        List<ReplyRequest> requestList = new ArrayList<>();
        ReplyRequest request1 = new ReplyRequest(3L, "test reply", "test user");
        ReplyRequest request2 = new ReplyRequest(3L, "댓글2", "admin");
        ReplyRequest request3 = new ReplyRequest(3L, "댓글 조회기능", "사용자1");
        ReplyRequest request4 = new ReplyRequest(3L, "게시글 조회시 한꺼번에 댓글 조회", "익명 사용자");
        ReplyRequest request5 = new ReplyRequest(3L, "게시글과 댓글 조회", "바코드닉");

        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
        requestList.add(request4);
        requestList.add(request5);
        Reply reply = new Reply();
        List<Reply> list = requestList.stream().map(s -> reply.createReply(s, communityForReply)).toList();

        replyRepository.saveAll(list);
    }



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