package com.msa.board.community.domain.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue
  //  @Column(name = "reply_id")
    private Long id;

    private String replyContent;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "community_id")
    private Community community;


    private LocalDateTime replyTime;
}
