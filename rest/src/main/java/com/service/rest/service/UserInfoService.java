package com.service.rest.service;

import com.service.rest.entity.UserInfo;
import com.service.rest.repository.UserInfoRepositoty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoRepositoty userInfoRepositoty;

    public UserInfoService(UserInfoRepositoty userInfoRepositoty) {
        this.userInfoRepositoty = userInfoRepositoty;
    }

    @Transactional
    public List<UserInfo> getAllUserInfo(){
        return userInfoRepositoty.getAll();
    }

    @Transactional
    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepositoty.saveUserInfo(userInfo);
    }

}
