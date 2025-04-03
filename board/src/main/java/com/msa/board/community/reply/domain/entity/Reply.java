package com.msa.board.community.reply.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msa.board.community.domain.Entity.Community;
import com.msa.board.community.reply.domain.request.ReplyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Setter(AccessLevel.PRIVATE)
    private String replyContent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Setter(AccessLevel.PRIVATE)
    private String username;

    private LocalDateTime replyTime;


    // 양방향 연관관계 메서드
    public void setCommunity(Community community) {
        this.community = community;
        community.getReply().add(this);
    }


    public Reply createReply(ReplyRequest request, Community community) {
        Reply reply = new Reply();
        reply.replyTime = LocalDateTime.now();
        reply.setCommunity(community);
        reply.setUsername(username);
        reply.setReplyContent(request.getReplyContent());
        return reply;
    }
}
