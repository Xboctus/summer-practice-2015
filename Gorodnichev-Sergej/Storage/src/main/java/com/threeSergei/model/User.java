package com.threeSergei.model;

/**
 * Created by sergej on 15.07.15.
 */
import javax.persistence.*;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic
    private String login;

    @Basic
    private String password;

    @Basic
    private String path;

    @Basic
    private String hash;

    @Basic
    private int role;

    @Basic
    private long store_id;

    @Basic
    private long common_space_id;
}