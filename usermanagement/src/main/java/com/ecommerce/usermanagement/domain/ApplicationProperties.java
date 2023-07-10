package com.ecommerce.usermanagement.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${server.port}")
    private int serverPort;

    public int getServerPort() {
        return serverPort;
    }
}
