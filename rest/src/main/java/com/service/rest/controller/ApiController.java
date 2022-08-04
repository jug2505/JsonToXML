package com.service.rest.controller;

import com.service.rest.entity.UserInfo;
import com.service.rest.service.UserInfoService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import org.w3c.dom.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class ApiController {
    private final UserInfoService userInfoService;

    public ApiController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //  На вход: информация о пользователе в JSON, который сохраняется в БД, далее JSON преобразуется XML,
    //  данные отправляются в SOAP-приложение. SOAP-приложение преобразует XML с помощью XSLT.
    //  После получения данных REST-приложением в БД сохраняется ответ и возвращается пользователю.
    @PostMapping("/json")
    public ResponseEntity<String> jsonToXML(@RequestBody String jsonString) {
        UserInfo userInfo;
        try {
            // JSON -> UserInfo
            userInfo = userInfoService.getUserInfoFromJson(jsonString);
        } catch (JSONException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Сохранение в БД
        userInfoService.saveUserInfo(userInfo);
        // xml из UserInfo
        String xml = XML.toString(new JSONObject(jsonString));

        // Подготовка запроса
        HttpPost httpPost =
                new HttpPost("http://localhost:8080/ws");
        httpPost.addHeader("content-type", "text/xml");

        String envelope = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
                " xmlns:gs=\"https://github.com/jug2505/JsonToXML\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<gs:getUserRequest>" +
                "<gs:data>" +
                "<![CDATA[<person>" + xml + "</person>]]>" +
                "</gs:data>" +
                "</gs:getUserRequest>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";

        StringEntity entity = new StringEntity(envelope, "UTF-8");
        httpPost.setEntity(entity);

        // Запрос к SOAP сервису, получение XML
        String result;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            result = result.replaceAll("&gt;", ">").replaceAll("&lt;", "<");

            System.out.println(result);
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Получение UserInfo из XML, сохранение в БД и создание xml из Node
        String resultXSLT = "";
        try{
            Node personNode = userInfoService.getPersonNodeFromXSLT(result);
            UserInfo userInfoFromXSLT = userInfoService.getUserInfoFromNode(personNode);
            userInfoService.saveUserInfo(userInfoFromXSLT);
            resultXSLT = userInfoService.nodeToString(personNode);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(resultXSLT, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<UserInfo> getAllUserInfo(){
        return userInfoService.getAllUserInfo();
    }
}
