package com.msa.board.community.domain.Entity;


import com.msa.board.community.domain.request.CommunityRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String username;
    @OneToMany
    private List<Reply> reply;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public Community(UserDetails userDetails, CommunityRequest communityRequest){
        this.title = communityRequest.getTitle();
        this.content = communityRequest.getContent();
        this.username = userDetails.getUsername();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();

    }
}
