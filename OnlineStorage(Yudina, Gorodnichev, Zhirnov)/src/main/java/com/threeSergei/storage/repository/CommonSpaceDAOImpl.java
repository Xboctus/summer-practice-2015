package com.threeSergei.storage.repository;

import com.threeSergei.storage.model.CommonSpaceEntity;
import com.threeSergei.storage.model.StoreEntity;
import com.threeSergei.storage.model.UserEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sergej on 17.07.15.
 */
@Repository
public class CommonSpaceDAOImpl implements CommonSpaceDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public CommonSpaceEntity get(int id) {
        Session session = sessionFactory.getCurrentSession();
        CommonSpaceEntity cs = (CommonSpaceEntity) session.load(CommonSpaceEntity.class, id);
        Hibernate.initialize(cs);
        return cs;
    }

    public void add(CommonSpaceEntity entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    public void update(CommonSpaceEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    public void remove(int storeId, int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<CommonSpaceEntity> csList = session.createQuery("from CommonSpaceEntity where userId = " + userId + " and storeId = " + storeId).list();
        if (!csList.isEmpty()) {
            session.delete(csList.get(0));
        }
    }

    public List getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CommonSpaceEntity> csList = session.createQuery("from CommonSpaceEntity ").list();
        return csList;
    }

    public List getCommonByUser(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<StoreEntity> csList = session.createQuery("select s from StoreEntity as s, CommonSpaceEntity as cs where s.id = cs.storeId and cs.userId = " + userId).list();
        return csList;
    }

    public List getUsersByCommon(int storeId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserEntity> csList = session.createQuery("select u from UserEntity as u, CommonSpaceEntity as cs where u.id = cs.userId and cs.storeId = " + storeId).list();
        return csList;
    }

    public boolean exist(int userId, int storeId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<CommonSpaceEntity> csList = session.createQuery("from CommonSpaceEntity where storeId = " + storeId + " and userId = " + userId).list();
        return !csList.isEmpty();
    }
}
