package com.msa.board.community.controller;

import com.msa.board.common.exception.UserException;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.domain.request.CommunityDeleteRequest;
import com.msa.board.community.domain.request.CommunityPost;
import com.msa.board.community.domain.request.CommunityRequest;
import com.msa.board.community.domain.response.CommunityListResponse;
import com.msa.board.community.domain.response.CommunityResponse;
import com.msa.board.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommunityController {

    private final CommunityService communityService;

    @Qualifier("blacklistRedisTemplate")
    private final RedisTemplate<String, Object> blacklistRedisTemplate;

    @GetMapping("/community")
    public ResponseEntity<List<CommunityListResponse>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        List<CommunityListResponse> all = communityService.findAll();
        return ResponseEntity
                .ok()
                .body(all);
    }

    @GetMapping("/community/{id}")
    public ResponseEntity<CommunityResponse> findOne(@CookieValue(value = "jwt", required = false) String token, @PathVariable Long id) {
        System.out.println("jwt: " + token);
        if(token == null || blacklistRedisTemplate.hasKey(token)){
            throw new UserException("로그아웃된 사용자입니다.");
        }
        CommunityResponse one = communityService.findOne(id);

        return ResponseEntity
                .ok()
                .body(one);
    }

    @PostMapping("/community")
    public ResponseEntity<?> addCommunity(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommunityPost post) {

        Community save = communityService.addCommunity(userDetails, post);

        return ResponseEntity
                .ok()
                .body(save);
    }

    @PutMapping("/community")
    public ResponseEntity<?> editCommunity(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommunityRequest community) {

        CommunityResponse communityResponse = communityService.editCommunity(userDetails, community);

        return ResponseEntity
                .ok()
                .body(communityResponse);
    }

    @DeleteMapping("/community")
    public ResponseEntity<?> deleteCommunity(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommunityDeleteRequest communityDeleteRequest) {
        Long deleteResponse = communityService.deleteCommunity(userDetails, communityDeleteRequest);
        return ResponseEntity
                .ok()
                .body(deleteResponse);
    }



}
