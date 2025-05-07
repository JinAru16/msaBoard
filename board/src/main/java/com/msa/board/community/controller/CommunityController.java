package com.msa.board.community.controller;

import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.domain.request.CommunityPost;
import com.msa.board.community.domain.request.CommunityRequest;
import com.msa.board.community.domain.response.BoardResponse;
import com.msa.board.community.domain.response.CommunityListResponse;
import com.msa.board.community.domain.response.CommunityResponse;
import com.msa.board.community.reply.domain.response.ReplyResponse;
import com.msa.board.community.reply.service.ReplyService;
import com.msa.board.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommunityController {

    private final CommunityService communityService;
    private final ReplyService replyService;

    @GetMapping("/community")
    public ResponseEntity<List<CommunityListResponse>> findAll() {
        List<CommunityListResponse> all = communityService.findAll();
        return ResponseEntity
                .ok()
                .body(all);
    }

    @GetMapping("/community/{id}")
    public ResponseEntity<BoardResponse> findOne(@CookieValue(value = "jwt", required = false) String token,
                                                     @RequestHeader Map<String, String> headers,
                                                     @PathVariable Long id) throws URISyntaxException {
        CommunityResponse one = communityService.findOne(id);
        List<ReplyResponse> allReply = replyService.findAllReply(id);

        return ResponseEntity
                .ok()
                .body(new BoardResponse(one, allReply));
    }

    @PostMapping("/community")
    public ResponseEntity<?> addCommunity(@RequestHeader Map<String, String> headers,
                                          @RequestBody CommunityPost post) {

        Community save = communityService.addCommunity(headers, post);

        return ResponseEntity
                .ok()
                .body(save);
    }

    @PutMapping("/community")
    public ResponseEntity<?> editCommunity(@RequestHeader Map<String, String> headers, @RequestBody CommunityRequest community) {

        CommunityResponse communityResponse = communityService.editCommunity(headers, community);

        return ResponseEntity
                .ok()
                .body(communityResponse);
    }

    @DeleteMapping("/community/{id}")
    public ResponseEntity<?> deleteCommunity(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        Long deleteResponse = communityService.deleteCommunity(headers, id);
        return ResponseEntity
                .ok()
                .body(deleteResponse);
    }



}
