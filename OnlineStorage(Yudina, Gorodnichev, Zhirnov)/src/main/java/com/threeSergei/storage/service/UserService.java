package com.threeSergei.storage.service;

import com.threeSergei.storage.model.UserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sergej on 16.07.15.
 */
public interface UserService {
    UserEntity get(int id);
    void add(UserEntity entity);
    void update(UserEntity entity);
    void remove(int id);
    List getAll();
    UserEntity getCurrent();
    UserEntity find(String login, String pass);
}