package com.vmware.tanzu.adapter;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JournalingConfiguration {

    // needed when calling via a proxy
    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return new RestClientHttpCustomizer();
    }
}
