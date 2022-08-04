package com.service.rest.controller;

import https.github_com.jug2505.jsontoxml.GetUserRequest;
import https.github_com.jug2505.jsontoxml.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class UserToXMLClient extends WebServiceGatewaySupport {

    public GetUserResponse getUserResponseXML(String data) {
        GetUserRequest request = new GetUserRequest();
        request.setData(data);

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
