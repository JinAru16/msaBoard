package com.msa.board.order;


import com.msa.board.common.MemberServerApiRequest;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class UserInfoServiceTest {
    @Autowired
    MemberServerApiRequest userInfoService;

    @Test
    @DisplayName("")
    void getMemberInfo() throws URISyntaxException {
        //given
        String urlAllMember = "http://localhost:8066/search/member";
        URI uriAll = new URI(urlAllMember);

        RestClient restClient = RestClient.builder()
                .baseUrl(urlAllMember)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // 쿠키 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "jwt=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzQ1OTkzMjc3LCJleHAiOjE3NDU5OTY4Nzd9.CGXukW6YFq45D9o_4kl-YZuJSqJ1IWGGnaRVwCnaiEE");

        //when
        ResponseEntity<List> entity = restClient.get()
                .uri(uriAll)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .toEntity(List.class);

        System.out.println("----");
        System.out.println(entity.getStatusCode().toString());
        entity.getBody().forEach(System.out::println);
        System.out.println("----");

        //then
        Assertions.assertThat(entity.getStatusCode().is2xxSuccessful()).isTrue();
        Assertions.assertThat(entity.getBody().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("")
    void getOneMember() throws URISyntaxException {
        //given
        String ulrOneMember = "http://localhost:8066/search/member/address";
        URI uriOne = new URI(ulrOneMember);
        URI uriTwo = new URI("member/address");


        RestClient restClient = RestClient.builder()
                .baseUrl(uriOne)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "jwt=\t\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzQ2NTgzNjg1LCJleHAiOjE3NDY1ODcyODV9.5OuJJz4_0GDPmKGuLHeLAXLe0apq5P3doJK-CozK4BE");

       System.out.println("uriTwo : " + uriTwo);

        //when
        ResponseEntity<Map> entity = restClient.get()
                .uri(uriTwo)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .toEntity(Map.class);

        //then
        System.out.println(entity.getStatusCode().toString());
        System.out.println(entity.getBody());

    }

    @Test
    @DisplayName("")
    void getAddressByUsernameTest() throws URISyntaxException {
        //given
        ResponseEntity<Map> addressByUsername = userInfoService.getAddressByUsername();
        //when
        System.out.println(addressByUsername.getBody());
        //then

    }
}
