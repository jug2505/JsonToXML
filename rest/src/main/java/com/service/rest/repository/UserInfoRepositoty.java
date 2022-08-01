package com.service.rest.repository;

import com.service.rest.entity.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoRepositoty {

    private final SessionFactory sessionFactory;

    public UserInfoRepositoty(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveUserInfo(UserInfo userInfo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(userInfo.getDocument());
        session.save(userInfo);

        session.close();
        session.getTransaction().commit();
    }

    // Возвращает всех пользователей
    public List<UserInfo> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserInfo> result = session.createQuery("from UserInfo", UserInfo.class).getResultList();

        session.close();
        session.getTransaction().commit();
        return result;
    }


}
