package com.threeSergei.storage.repository;

import com.threeSergei.storage.model.CommonSpaceEntity;

import java.util.List;

/**
 * Created by sergej on 20.07.15.
 */
public interface CommonSpaceDAO {
    CommonSpaceEntity get(int id);
    void add(CommonSpaceEntity entity);
    void update(CommonSpaceEntity entity);
    void remove(int storeId, int userId);
    List getAll();
    List getCommonByUser(int userId);
    List getUsersByCommon(int storeId);
    boolean exist(int userId, int storeId);
}
