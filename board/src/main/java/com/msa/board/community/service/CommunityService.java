package com.msa.board.community.service;

import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.domain.request.CommunityRequest;
import com.msa.board.community.domain.response.CommunityResponse;
import com.msa.board.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    public Community addCommunity(UserDetails userDetails, CommunityRequest communityRequest) {
        Community community = new Community(userDetails, communityRequest);
         return communityRepository.save(community);
    }


    public CommunityResponse findOne(Long id) {
        Optional<Community> posted = communityRepository.findById(id);

        return new CommunityResponse(posted.get());

    }
}
