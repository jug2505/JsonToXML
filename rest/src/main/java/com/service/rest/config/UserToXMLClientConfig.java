package com.service.rest.config;

import com.service.rest.controller.UserToXMLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class UserToXMLClientConfig {
    @Value("${client.config.context.path}")
    private String CONTEXT_PATH = "";

    @Value("${soap.uri}")
    private String SOAP_URI = "";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(CONTEXT_PATH);
        return marshaller;
    }

    @Bean
    public UserToXMLClient userToXMLClient(Jaxb2Marshaller marshaller) {
        UserToXMLClient client = new UserToXMLClient();
        client.setDefaultUri(SOAP_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
