package com.threeSergei.storage.repository;

import com.threeSergei.storage.model.StoreEntity;
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
public class StoreDAOImpl implements StoreDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public StoreEntity get(int id) {
        Session session = sessionFactory.getCurrentSession();
        StoreEntity store = (StoreEntity) session.load(StoreEntity.class, new Integer(id));
        Hibernate.initialize(store);
        return store;
    }

    public void add(StoreEntity entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    public void update(StoreEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        StoreEntity store = (StoreEntity) session.load(StoreEntity.class, id);
        if(store != null) {
            session.delete(store);
        }
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<StoreEntity> storeList = session.createQuery("from StoreEntity ").list();
        return storeList;
    }

    public List getChildren(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        List<StoreEntity> storeList = session.createQuery("from StoreEntity where parentId = " + id + " order by type desc").list();
        return storeList;
    }

    public String getPath(int id) {
        StoreEntity temp = this.get(id);
        String path = temp.getName();
        while(temp.getParentId() != null) {
            int sid = temp.getParentId();
            temp = this.get(sid);
            path = temp.getName() + "/" + path;
        }
        return path;
    }

    public boolean isNameUnique(int id, String name) {
        List<StoreEntity> storeList = this.getChildren(id);
        if (!storeList.isEmpty()) {
            for (StoreEntity store : storeList) {
                if (store.getName().equals(name))
                    return false;
            }
        }
        return true;
    }
}
