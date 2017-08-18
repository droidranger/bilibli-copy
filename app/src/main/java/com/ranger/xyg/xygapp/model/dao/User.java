package com.ranger.xyg.xygapp.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xyg on 2017/4/24.
 */
@Entity
public class User {
    @Id
    private Long id;
    @Property(nameInDb = "USERNAME")
    private String userName;
    @Property(nameInDb = "NICKNAME")
    private String nickName;
    @Property(nameInDb = "AVATARURL")
    private String avatarUrl;
    @Generated(hash = 1982912214)
    public User(Long id, String userName, String nickName, String avatarUrl) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getAvatarUrl() {
        return this.avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
