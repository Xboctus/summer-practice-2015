package com.threeSergei.storage.service;

import com.threeSergei.storage.model.StoreEntity;
import com.threeSergei.storage.repository.StoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sergej on 20.07.15.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDAO storeDAO;

    public StoreEntity get(int id) {
        return storeDAO.get(id);
    }

    public void add(StoreEntity entity) {
        storeDAO.add(entity);
    }

    public void update(StoreEntity entity) {
        storeDAO.update(entity);
    }

    public void remove(int id) {
        storeDAO.remove(id);
    }

    public List getAll() {
        return storeDAO.getAll();
    }

    public List getChildren(int id) {
        return storeDAO.getChildren(id);
    }

    public String getPath(int id) {
        return storeDAO.getPath(id);
    }

    public boolean isNameUnique(int id, String name) {
        return storeDAO.isNameUnique(id, name);
    }
}
