package com.service.soap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

//@Endpoint
//public class SoapEndpoint {
//
//    @PayloadRoot(localPart = "ServiceRequest")
//    @ResponsePayload
//    public String getService(){
//        return "test";
//    }
//}
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import https.github_com.jug2505.jsontoxml.GetUserRequest;
import https.github_com.jug2505.jsontoxml.GetUserResponse;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

@Endpoint
public class SoapEndpoint {
    private static final String NAMESPACE_URI = "https://github.com/jug2505/JsonToXML";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getCountry(@RequestPayload GetUserRequest request) {
        String resultXSLT = "";
        try {
            System.out.println(request.getData());
            resultXSLT = XMLLtoXSLTTransformer.transform(request.getData());
        } catch (TransformerException | FileNotFoundException ignored) {}

        GetUserResponse response = new GetUserResponse();
        response.setData(resultXSLT);
        return response;
    }
}