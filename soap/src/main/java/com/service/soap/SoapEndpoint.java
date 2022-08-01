package com.service.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapEndpoint {

    @PayloadRoot(localPart = "ServiceRequest")
    @ResponsePayload
    public String getService(){
        return "test";
    }
}
