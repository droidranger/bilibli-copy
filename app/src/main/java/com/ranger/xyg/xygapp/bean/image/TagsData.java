package com.ranger.xyg.xygapp.bean.image;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyg on 2017/6/14.
 */

class TagsData {
    /**
     * tagId : 45398
     * poiId : 45398
     * topCategory : 5
     * tagDescription : null
     * tagName : 曼谷
     * imgUrl : null
     * layoutType : 0
     * recommend : false
     * open : false
     * appNavUrl : tuniuapp://page?iosPageName=TNDCPhotoListByPoiViewController&androidPageName=com.tuniu.community.activity.travel.TravelPictureListActivity&pluginType=6¶meters=%7B%22travel_pic_tag_flag%22%3A1%2C%22tag_top_category%22%3A5%2C%22tag_name%22%3A%22%E6%9B%BC%E8%B0%B7%22%2C%22tag_id%22%3A45398%2C%22poi_id%22%3A45398%7D
     * h5NavUrl : https://m.tuniu.com/community/travellist/45398/%E6%9B%BC%E8%B0%B7?tripImgFlag=1
     * hot : 0
     * contentNum : 0
     * subscribed : false
     * date : null
     * tripImgFlag : 1
     */

    private int tagId;
    private int poiId;
    private int topCategory;
    private Object tagDescription;
    private String tagName;
    private Object imgUrl;
    private int layoutType;
    private boolean recommend;
    private boolean open;
    private String appNavUrl;
    private String h5NavUrl;
    private int hot;
    private int contentNum;
    private boolean subscribed;
    private Object date;
    private int tripImgFlag;

    public static TagsData objectFromData(String str) {

        return new Gson().fromJson(str, TagsData.class);
    }

    public static List<TagsData> arrayTagsnullFromData(String str) {

        Type listType = new TypeToken<ArrayList<TagsData>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public int getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(int topCategory) {
        this.topCategory = topCategory;
    }

    public Object getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(Object tagDescription) {
        this.tagDescription = tagDescription;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getAppNavUrl() {
        return appNavUrl;
    }

    public void setAppNavUrl(String appNavUrl) {
        this.appNavUrl = appNavUrl;
    }

    public String getH5NavUrl() {
        return h5NavUrl;
    }

    public void setH5NavUrl(String h5NavUrl) {
        this.h5NavUrl = h5NavUrl;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getContentNum() {
        return contentNum;
    }

    public void setContentNum(int contentNum) {
        this.contentNum = contentNum;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public int getTripImgFlag() {
        return tripImgFlag;
    }

    public void setTripImgFlag(int tripImgFlag) {
        this.tripImgFlag = tripImgFlag;
    }
}
