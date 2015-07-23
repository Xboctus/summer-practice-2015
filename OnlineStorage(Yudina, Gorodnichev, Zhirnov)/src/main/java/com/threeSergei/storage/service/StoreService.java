package com.threeSergei.storage.service;

import com.threeSergei.storage.model.StoreEntity;

import java.util.List;

/**
 * Created by sergej on 20.07.15.
 */
public interface StoreService {
    StoreEntity get(int id);
    void add(StoreEntity entity);
    void update(StoreEntity entity);
    void remove(int id);
    List getAll();
    List getChildren(int id);
    String getPath(int id);
    boolean isNameUnique(int id, String name);
}
