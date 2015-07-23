package com.threeSergei.storage.service;

import com.threeSergei.storage.model.UserEntity;
import com.threeSergei.storage.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sergej on 17.07.15.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    public UserEntity get(int id) {
        return userDAO.get(id);
    }

    public void add(UserEntity entity) {
        userDAO.add(entity);
    }

    public void update(UserEntity entity) {
        userDAO.update(entity);
    }

    public void remove(int id) {
        userDAO.remove(id);
    }

    public List getAll() {
        return userDAO.getAll();
    }

    public UserEntity getCurrent() {
        return userDAO.getCurrent();
    }

    public UserEntity find(String login, String pass) {
        return userDAO.find(login, pass);
    }
}
