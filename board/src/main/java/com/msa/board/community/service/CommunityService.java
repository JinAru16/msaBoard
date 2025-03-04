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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    public Community addCommunity(UserDetails userDetails, CommunityPost post) {
        Community community = new Community(userDetails, post);
         return communityRepository.save(community);
    }


    public CommunityResponse findOne(Long id) {
        Optional<Community> posted = communityRepository.findById(id);
        return new CommunityResponse(posted.get());
    }

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
