package com.msa.board.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
public class MemberServerApiRequest {
    private String baseUrl = "http://localhost:8066/search/member";

    public ResponseEntity<Map> getAddressByUsername() throws URISyntaxException {
        String uri = "/address";
        return getOneDataFromApi(uri);
    }
    //단건리턴
    private ResponseEntity<Map> getOneDataFromApi(String  uri) throws URISyntaxException {
        //authentication에서 토큰 불러오기
        RestClient client = setRestClientData(uri);
        HttpHeaders headers = setHeaders();

        return client
                .get()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .toEntity(Map.class);
    }

    // 리스트 리턴
    private ResponseEntity<List> getDataListFromApi(String url) throws URISyntaxException {
        //authentication에서 토큰 불러오기
        RestClient client = setRestClientData(url);
        HttpHeaders headers = setHeaders();

        return client
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .toEntity(List.class);
    }

    private RestClient setRestClientData(String url) throws URISyntaxException {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private HttpHeaders setHeaders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "jwt="+token);
        return headers;
    }

}
