package com.msa.board.order;

import com.msa.common.entity.Role;
import com.msa.common.entity.Users;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private Role role;

    public MemberResponse(Users users) {
        this.id = users.getId();
        this.username = users.getUsername();
        this.nickname = users.getNickname();
        this.email = users.getEmail();
        this.role = users.getRole();
    }
}
