package com.msa.board.community.service;

import com.msa.board.common.exception.BoardException;
import com.msa.board.common.exception.UserException;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.domain.request.CommunityDeleteRequest;
import com.msa.board.community.domain.request.CommunityPost;
import com.msa.board.community.domain.request.CommunityRequest;
import com.msa.board.community.domain.response.CommunityListResponse;
import com.msa.board.community.domain.response.CommunityResponse;
import com.msa.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;


    @Cacheable(value = "community", key = "#id", cacheManager = "boardCacheManager")
    public CommunityResponse findOne(Long id) {
        return communityRepository.findById(id)
                .map(CommunityResponse::new)
                .orElseThrow(() -> new BoardException("해당 게시글이 존재하지않습니다."));
    }


    public Community addCommunity(UserDetails userDetails, CommunityPost post) {
        Community community = new Community(userDetails, post);
        return communityRepository.save(community);
    }


    @CachePut(value = "community", key = "#communityRequest.getId()", cacheManager = "boardCacheManager")
    public CommunityResponse editCommunity(UserDetails userDetails, CommunityRequest communityRequest) {

        Optional<Community> communityToEdit = communityRepository.findById(communityRequest.getId());
        if (communityToEdit.isPresent()) {
            if(isSameUser(userDetails, communityToEdit.get().getUsername()) || isAdmin(userDetails)){
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

    public Long deleteCommunity(UserDetails userDetails, CommunityDeleteRequest communityDeleteRequest) {
        if(isSameUser(userDetails, communityDeleteRequest.getUsername()) || isAdmin(userDetails)) {
            communityRepository.deleteById(communityDeleteRequest.getId());
            return communityDeleteRequest.getId();
        }else{
            throw new UserException("삭게 권한이 없습니다.");
        }
    }

    private boolean isSameUser(UserDetails userDetails, String username){
        return userDetails.getUsername().equals(username);
    }

    private boolean isAdmin(UserDetails userDetails) {
        return false;
    }

    public List<CommunityListResponse> findAll() {
        return communityRepository.findAll().stream().map(CommunityListResponse::new).collect(Collectors.toList());
    }
}
