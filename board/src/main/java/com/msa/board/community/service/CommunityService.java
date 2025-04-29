package com.msa.board.community.service;

import com.msa.board.common.exception.BoardException;
import com.msa.common.exception.UserException;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.domain.request.CommunityPost;
import com.msa.board.community.domain.request.CommunityRequest;
import com.msa.board.community.domain.response.CommunityListResponse;
import com.msa.board.community.domain.response.CommunityResponse;
import com.msa.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommunityService {
    private final CommunityRepository communityRepository;


    @Cacheable(value = "community", key = "#id", cacheManager = "boardCacheManager")
    public CommunityResponse findOne(Long id) {
        log.info("[Cache Miss] DB 조회 실행 - 게시글 ID: {}", id);
        return communityRepository.findById(id)
                .map(CommunityResponse::new)
                .orElseThrow(() -> new BoardException("해당 게시글이 존재하지않습니다."));
    }


    public Community addCommunity(Map<String, String> headers, CommunityPost post) {
        String username = headers.get("x-auth-id");
        Community community = new Community(username, post);
        log.info("Community Inserted : {}, {}, {}", community.getTitle(), community.getUsername(), community.getCreateTime());
        return communityRepository.save(community);
    }


    @CachePut(value = "community", key = "#communityRequest.getId()", cacheManager = "boardCacheManager")
    public CommunityResponse editCommunity(Map<String, String> headers, CommunityRequest communityRequest) {

        String username = headers.get("x-auth-id");
        String role = headers.get("x-auth-role");

        Optional<Community> communityToEdit = communityRepository.findById(communityRequest.getId());
        log.info("[Cache Update] 게시글 수정 - ID: {}, 요청자: {}", communityRequest.getId(), username);
        if (communityToEdit.isPresent()) {
            if(isSameUser(username, communityToEdit.get().getUsername()) || isAdmin(role)){
                communityToEdit.get().editTitle(communityRequest.getTitle());
                communityToEdit.get().editContent(communityRequest.getContent());
                return new CommunityResponse(communityRepository.save(communityToEdit.get()));
            } else{
                throw new UserException("수정권한이 없습니다.");
            }
        } else{
            throw new NoSuchElementException("수정할 게시글이 존재하지않습니다.");
        }

    }

    @CacheEvict(value = "community", key = "#id", cacheManager = "boardCacheManager")
    public Long deleteCommunity(Map<String, String> headers, Long id) {
        Optional<Community> toDelete = communityRepository.findById(id);
        if (toDelete.isPresent()) {
            if(isSameUser(headers.get("x-auth-id"), toDelete.get().getUsername()) || isAdmin(headers.get("x-auth-role"))){
                communityRepository.deleteById(id);
                log.warn("[Cache Evict] 게시글 삭제 - ID: {}, 요청자: {}", id, headers.get("x-auth-id"));
                return id;
            } else{
                throw new UserException("삭제권한이 없습니다.");
            }

        } else{
            throw new BoardException("게시글이 존재하지않습니다.");
        }
    }

    private boolean isSameUser(String headerUsername, String username){
        return headerUsername.equals(username);
    }

    private boolean isAdmin(String headerUserRole) {
        return headerUserRole.equals("admin");
    }

    public List<CommunityListResponse> findAll() {
        return communityRepository.findAll().stream().map(CommunityListResponse::new).collect(Collectors.toList());
    }
}
