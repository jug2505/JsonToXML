package com.service.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.service.rest.entity.UserInfo;
import com.service.rest.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import https.github_com.jug2505.jsontoxml.GetUserResponse;

@RestController()
@RequestMapping("/api")
public class ApiController {
    Logger logger = LoggerFactory.getLogger(ApiController.class);
    private final UserInfoService userInfoService;
    private final UserToXMLClient userToXMLClient;

    public ApiController(UserInfoService userInfoService, UserToXMLClient userToXMLClient) {
        this.userInfoService = userInfoService;
        this.userToXMLClient = userToXMLClient;
    }

    //  На вход: информация о пользователе в JSON, который сохраняется в БД, далее JSON преобразуется XML,
    //  данные отправляются в SOAP-приложение. SOAP-приложение преобразует XML с помощью XSLT.
    //  После получения данных REST-приложением в БД сохраняется ответ и возвращается пользователю.
    @PostMapping("/json")
    public ResponseEntity<String> jsonToXML(@RequestBody UserInfo userInfo){
        logger.info("UserInfo received: " + userInfo);
        if (userInfo.isFieldsNull()) {
            logger.error("Fields not set:  " + UserInfo.class.getSimpleName());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Сохранение в БД
        userInfoService.saveUserInfo(userInfo);
        // xml из UserInfo
        XmlMapper xmlMapper = new XmlMapper();
        String xml;
        try {
            xml = xmlMapper.writeValueAsString(userInfo).replace(UserInfo.class.getSimpleName(), "person");
        } catch (JsonProcessingException ex) {
            logger.error("JsonProcessingException while mapping UserInfo -> XML");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("XML send to SOAP service: " + xml);
        GetUserResponse getUserResponse = userToXMLClient.getUserResponseXML(xml);

        String responseXML = getUserResponse.getData();
        UserInfo secondUserInfo;
        try {
            secondUserInfo = xmlMapper.readValue(responseXML, UserInfo.class);
        } catch (JsonProcessingException ex) {
            logger.error("JsonProcessingException while mapping XML -> UserInfo");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("UserInfo received from SOAP service: " + secondUserInfo);
        userInfoService.saveUserInfo(secondUserInfo);

        return new ResponseEntity<>(responseXML, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<UserInfo> getAllUserInfo(){
        return userInfoService.getAllUserInfo();
    }
}
