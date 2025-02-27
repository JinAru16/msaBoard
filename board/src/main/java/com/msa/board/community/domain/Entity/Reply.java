package com.msa.board.community.domain.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue
    private Long id;

    private String replyContent;

    @ManyToOne
    private Community community;


    private LocalDateTime replyTime;
}
