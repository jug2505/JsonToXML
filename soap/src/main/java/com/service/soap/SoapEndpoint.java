package com.service.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import https.github_com.jug2505.jsontoxml.GetUserRequest;
import https.github_com.jug2505.jsontoxml.GetUserResponse;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

@Endpoint
public class SoapEndpoint {
    Logger logger = LoggerFactory.getLogger(SoapEndpoint.class);
    private static final String NAMESPACE_URI = "https://github.com/jug2505/JsonToXML";

    // SOAP метод принимающий данные в CDATA
    // Парсинг XML
    // Преобразование структуры XML при помощи XSLT
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUserXML(@RequestPayload GetUserRequest request) {
        String resultXSLT = "";
        try {
            logger.info("Data received: " + request.getData());
            resultXSLT = XMLLtoXSLTTransformer.transform(request.getData());
        } catch (TransformerException | FileNotFoundException ex) {
            logger.error("Error while transforming xml with xslt");
        }

        GetUserResponse response = new GetUserResponse();
        response.setData(resultXSLT);
        logger.info("XML send: " + resultXSLT);
        return response;
    }
}