package com.threeSergei.storage.repository;

import com.threeSergei.storage.model.UserEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sergej on 17.07.15.
 */
@Repository
public class UserDAOImpl implements UserDAO{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private HttpSession httpSession;

    public UserEntity get(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserEntity user = (UserEntity) session.load(UserEntity.class, id);
        Hibernate.initialize(user);
        return user;
    }

    public void add(UserEntity entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    public void update(UserEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserEntity user = (UserEntity) session.load(UserEntity.class, id);
        if(user != null) {
            session.delete(user);
        }
    }

    public List getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserEntity> userList = session.createQuery("from UserEntity ").list();
        return userList;
    }

    public UserEntity getCurrent() {
        synchronized (httpSession) {
            if (httpSession.getAttribute("id") != null && httpSession.getAttribute("hash") != null) {
                UserEntity user = this.get((Integer)httpSession.getAttribute("id"));
                String sessionHash = (String) httpSession.getAttribute("hash");
                if (user != null && user.getHash().equals(sessionHash)) {
                    return user;
                }
            }
            return null;
        }
    }

    public UserEntity find(String login, String pass) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserEntity> userList = session.createQuery("from UserEntity where login = \'" + login + "\' and password = \'" + pass + "\'").list();
        return (!userList.isEmpty()) ? userList.get(0) : null;
    }
}
