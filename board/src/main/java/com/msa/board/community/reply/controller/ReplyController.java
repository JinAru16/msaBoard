package com.msa.board.community.reply.controller;

import com.msa.board.community.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ReplyController {

    private final ReplyService replyService;

//    @GetMapping("/reply/{id}")
//    public ResponseEntity<?> allReply(@PathVariable Long id){
//        replyService.findAllReply(id);
//    }
}
