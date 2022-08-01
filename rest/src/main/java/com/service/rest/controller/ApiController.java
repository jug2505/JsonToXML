package com.service.rest.controller;

import com.service.rest.entity.UserInfo;
import com.service.rest.service.UserInfoService;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class ApiController {
    private final UserInfoService userInfoService;

    public ApiController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/json")
    public ResponseEntity<String> jsonToXML(@RequestBody String jsonString){
        UserInfo userInfo;
        try {
            userInfo = userInfoService.getUserInfoFromJson(jsonString);
        } catch (JSONException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userInfoService.saveUserInfo(userInfo);
        String xml = XML.toString(new JSONObject(jsonString));
        return new ResponseEntity<>(xml, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<UserInfo> getAllUserInfo(){
        return userInfoService.getAllUserInfo();
    }
}
