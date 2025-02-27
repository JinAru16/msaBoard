package com.msa.board.community.controller;

import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.domain.request.CommunityRequest;
import com.msa.board.community.domain.response.CommunityResponse;
import com.msa.board.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/find/{id}")
    public ResponseEntity<CommunityResponse> findOne(@PathVariable Long id) {

        CommunityResponse one = communityService.findOne(id);

        return ResponseEntity
                .ok()
                .body(one);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCommunity(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommunityRequest community) {

        Community save = communityService.addCommunity(userDetails, community);

        return ResponseEntity
                .ok()
                .body(save);
    }



}
