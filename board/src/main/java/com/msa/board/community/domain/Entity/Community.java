package com.msa.board.community.domain.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msa.board.community.domain.request.CommunityPost;
import com.msa.board.community.domain.request.CommunityRequest;
import jakarta.persistence.*;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
   // @JoinColumn(name = "reply_id")
    private List<Reply> reply;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public void editContent(String content){
        this.content = content;
    }

    public void editTitle(String title){
        this.title = title;
    }

    public Community(UserDetails userDetails, CommunityRequest communityRequest){
        this.title = communityRequest.getTitle();
        this.content = communityRequest.getContent();
        this.username = userDetails.getUsername();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();

    }

    public Community(UserDetails userDetails, CommunityPost post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = userDetails.getUsername();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
