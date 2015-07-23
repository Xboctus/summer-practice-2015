package com.threeSergei.storage.service;

import com.threeSergei.storage.model.CommonSpaceEntity;
import com.threeSergei.storage.repository.CommonSpaceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sergej on 20.07.15.
 */
@Service
@Transactional
public class CommonSpaceServiceImpl implements CommonSpaceService {
    @Autowired
    private CommonSpaceDAO commonSpaceDAO;

    public CommonSpaceEntity get(int id) {
        return null;
    }

    public void add(CommonSpaceEntity entity) {
        commonSpaceDAO.add(entity);
    }

    public void update(CommonSpaceEntity entity) {

    }

    public void remove(int storeId, int userId) {
        commonSpaceDAO.remove(storeId, userId);
    }

    public List getAll() {
        return null;
    }

    public List getCommonByUser(int userId) {
        return commonSpaceDAO.getCommonByUser(userId);
    }

    public List getUsersByCommon(int storeId) {
        return commonSpaceDAO.getUsersByCommon(storeId);
    }

    public boolean exist(int userId, int storeId) {
        return commonSpaceDAO.exist(userId, storeId);
    }
}
