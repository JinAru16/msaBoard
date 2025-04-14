package com.msa.board.community.reply.repository;

import com.msa.board.community.reply.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT R FROM Reply R WHERE R.community.id = :id")
    List<Reply> findAllReplyByCommunityId(Long id);
}
