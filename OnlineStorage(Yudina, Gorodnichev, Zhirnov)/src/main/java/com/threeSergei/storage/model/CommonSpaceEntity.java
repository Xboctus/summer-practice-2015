package com.threeSergei.storage.model;

import javax.persistence.*;

/**
 * Created by sergej on 22.07.15.
 */
@Entity
@Table(name = "common_space", schema = "", catalog = "storage")
public class CommonSpaceEntity {
    private int id;
    private int userId;
    private int storeId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "store_id", nullable = false, insertable = true, updatable = true)
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonSpaceEntity that = (CommonSpaceEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (storeId != that.storeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + storeId;
        return result;
    }
}
