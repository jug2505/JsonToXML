package com.service.rest.service;

import com.service.rest.entity.Document;
import com.service.rest.entity.DocumentType;
import com.service.rest.entity.Gender;
import com.service.rest.entity.UserInfo;
import com.service.rest.repository.UserInfoRepositoty;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoRepositoty userInfoRepositoty;

    public UserInfoService(UserInfoRepositoty userInfoRepositoty) {
        this.userInfoRepositoty = userInfoRepositoty;
    }

    public UserInfo getUserInfoFromJson(String string) throws JSONException {
        UserInfo userInfo = new UserInfo();
        JSONObject json = new JSONObject(string);
        userInfo.setName(json.getString("name"));
        userInfo.setSurname(json.getString("surname"));
        userInfo.setPatronymic(json.getString("patronymic"));
        userInfo.setBirthDate(Date.valueOf(json.getString("birthDate")));
        userInfo.setGender(Gender.valueOf(json.getString("gender")));
        JSONObject docJson = json.getJSONObject("document");

        Document document = new Document();
        document.setSeries(docJson.getString("series"));
        document.setNumber(docJson.getString("number"));
        document.setType(DocumentType.valueOf(docJson.getString("type")));
        document.setIssueDate(Date.valueOf(docJson.getString("issueDate")));

        userInfo.setDocument(document);

        return userInfo;
    }

    public List<UserInfo> getAllUserInfo(){
        return userInfoRepositoty.getAll();
    }

    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepositoty.saveUserInfo(userInfo);
    }

    public String convertUserInfoToXML(UserInfo userInfo) {
        JSONObject json = new JSONObject(userInfo);
        return XML.toString(userInfo);
    }
}
