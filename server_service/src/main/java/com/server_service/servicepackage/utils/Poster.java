package com.server_service.servicepackage.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Builder
@AllArgsConstructor
public class Poster {
    private String url;
    private Object object;
    private Class aClassObject;
    private Class aClassReturn;

    public Object post() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity<Object> request = new HttpEntity<Object>(object, httpHeaders);
        return restTemplate.postForObject(url, request, aClassReturn);
    }
}
