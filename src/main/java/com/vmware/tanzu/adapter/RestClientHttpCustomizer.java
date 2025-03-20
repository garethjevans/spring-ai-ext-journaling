package com.vmware.tanzu.adapter;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

public class RestClientHttpCustomizer implements RestClientCustomizer {

    @Override
    public void customize(RestClient.Builder restClientBuilder) {
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        restClientBuilder.requestFactory(new JdkClientHttpRequestFactory(httpClient));
    }
}
