package com.msa.board.community.reply.controller;

import com.msa.board.community.reply.domain.request.ReplyRequest;
import com.msa.board.community.reply.domain.response.ReplyResponse;
import com.msa.board.community.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("{id}/reply")// 게시글의 댓글만 조회.
    public ResponseEntity<?> allReply(@PathVariable Long id){
        List<ReplyResponse> allReply = replyService.findAllReply(id);
        return ResponseEntity
                .ok()
                .body(allReply);
    }

    @PostMapping("{id}/reply")
    public ResponseEntity<?> addReply(@RequestBody ReplyRequest replyRequest){
        ReplyResponse replyResponse = replyService.addReply(replyRequest);

        return ResponseEntity
                .ok()
                .body(replyResponse);
    }
}
