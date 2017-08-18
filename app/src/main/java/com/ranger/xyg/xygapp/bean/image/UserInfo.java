package com.ranger.xyg.xygapp.bean.image;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyg on 2017/6/14.
 */

class UserInfo {
    /**
     * userId : 27051789
     * userName : 8019395390
     * nickName : 微醉好入眠
     * headImage : http://m.tuniucdn.com/fb2/t1/G1/M00/3D/27/Cii9EVZf5MiIfSY9AAGwS4Q0JbUAAAzJwFUJusAAbBj427_w120_h120_c1_t0.jpg
     * level : 0
     * sex : 0
     * tag : null
     * age : 29
     * largeAvatar : null
     * custIndentity : 1557D7F4FF92EF7FE284AE46F77B3272
     * userFollowStatus : 0
     * tagType : 0
     */

    private int userId;
    private String userName;
    private String nickName;
    private String headImage;
    private int level;
    private int sex;
    private Object tag;
    private int age;
    private Object largeAvatar;
    private String custIndentity;
    private int userFollowStatus;
    private int tagType;

    public static UserInfo objectFromData(String str) {

        return new Gson().fromJson(str, UserInfo.class);
    }

    public static List<UserInfo> arrayUserInfonullFromData(String str) {

        Type listType = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Object getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(Object largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public String getCustIndentity() {
        return custIndentity;
    }

    public void setCustIndentity(String custIndentity) {
        this.custIndentity = custIndentity;
    }

    public int getUserFollowStatus() {
        return userFollowStatus;
    }

    public void setUserFollowStatus(int userFollowStatus) {
        this.userFollowStatus = userFollowStatus;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }
}
