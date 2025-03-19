package com.msa.board.email.domain;

import lombok.Getter;

@Getter
public enum MailSecurity {
    SMPT, STARTTLS, SSL;
}
