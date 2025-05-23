package com.msa.board.community.controller;

import com.msa.board.common.MemberServerApiRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RequestMemberApiController {
    private final MemberServerApiRequestService memberServerApiRequestService;

    @GetMapping("/member/address")
    public ResponseEntity<?> getAddressFromApi() throws URISyntaxException {
        ResponseEntity<Map> addressByUsername = memberServerApiRequestService.getAddressByUsername();
        return ResponseEntity.ok().body(addressByUsername.getBody());
    }

}
