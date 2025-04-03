package com.msa.board.community.reply.service;

import com.msa.board.community.reply.domain.response.ReplyResponse;
import com.msa.board.community.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<ReplyResponse> findAllReply(Long id) {
        //replyRepository.findAllById(id);
        return null;
    }

    public void addReply() {

    }
}
