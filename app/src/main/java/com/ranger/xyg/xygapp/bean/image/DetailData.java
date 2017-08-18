package com.ranger.xyg.xygapp.bean.image;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyg on 2017/6/14.
 */

class DetailData {
    /**
     * contentId : 2012268
     * tripImgId : 1716
     * imgUrl : https://m.tuniucdn.com/fb2/t1/G2/M00/31/3F/Cii-T1g-MOKIUilfALfwbwLBdd8AAE-pgJX7ycAt_CH10_w640_h0_c0_t0.jpeg
     * imgWidth : 4928
     * imgHeight : 3290
     * imgExif :
     * tags : [{"tagId":45398,"poiId":45398,"topCategory":5,"tagDescription":null,"tagName":"曼谷","imgUrl":null,"layoutType":0,"recommend":false,"open":false,"appNavUrl":"tuniuapp://page?iosPageName=TNDCPhotoListByPoiViewController&androidPageName=com.tuniu.community.activity.travel.TravelPictureListActivity&pluginType=6¶meters=%7B%22travel_pic_tag_flag%22%3A1%2C%22tag_top_category%22%3A5%2C%22tag_name%22%3A%22%E6%9B%BC%E8%B0%B7%22%2C%22tag_id%22%3A45398%2C%22poi_id%22%3A45398%7D","h5NavUrl":"https://m.tuniu.com/community/travellist/45398/%E6%9B%BC%E8%B0%B7?tripImgFlag=1","hot":0,"contentNum":0,"subscribed":false,"date":null,"tripImgFlag":1},{"tagId":19303,"poiId":0,"topCategory":7,"tagDescription":null,"tagName":"极致风光","imgUrl":"https://m.tuniucdn.com/fb2/t1/G2/M00/2D/51/Cii-T1g8FFuIeaZVAAISAGZ8OQgAAE72QPva1gAAhIY98_w640_h0_c0_t0.jpeg","layoutType":0,"recommend":false,"open":false,"appNavUrl":"tuniuapp://page?iosPageName=TNDCPhotoListByPoiViewController&androidPageName=com.tuniu.community.activity.travel.TravelPictureListActivity&pluginType=6¶meters=%7B%22travel_pic_tag_flag%22%3A0%2C%22tag_top_category%22%3A7%2C%22tag_name%22%3A%22%E6%9E%81%E8%87%B4%E9%A3%8E%E5%85%89%22%2C%22tag_id%22%3A19303%2C%22poi_id%22%3A0%7D","h5NavUrl":"https://m.tuniu.com/community/travellist/19303/%E6%9E%81%E8%87%B4%E9%A3%8E%E5%85%89?tripImgFlag=0","hot":0,"contentNum":0,"subscribed":false,"date":null,"tripImgFlag":0}]
     * userInfo : {"userId":27051789,"userName":"8019395390","nickName":"微醉好入眠","headImage":"http://m.tuniucdn.com/fb2/t1/G1/M00/3D/27/Cii9EVZf5MiIfSY9AAGwS4Q0JbUAAAzJwFUJusAAbBj427_w120_h120_c1_t0.jpg","level":0,"sex":0,"tag":null,"age":29,"largeAvatar":null,"custIndentity":"1557D7F4FF92EF7FE284AE46F77B3272","userFollowStatus":0,"tagType":0}
     * imgDesc : 好看的宫殿
     * hasPraised : false
     * time : 2016-11-30
     * h5Url : https://m.tuniu.com/community/traveldetail/2012268
     * hotIndex : 0
     * praiseCnt : 5
     * commentCnt : 0
     * shareCnt : 0
     * recommendFlag : 0
     * poiType : 2
     * destPoiId : 45398
     * destPoiName : 曼谷
     * createTime : 2016-11-30
     * fullTextPoi : 亚洲 泰国 曼谷
     * currentType : 0
     * excellent : true
     * state : 2
     * poiList : [{"poiId":3910,"poiName":"泰国","appNavUrl":"tuniuapp://page?iosPageName=TNDestinationStationViewController&androidPageName=com.tuniu.app.ui.activity.DestinationStationActivity¶meters={\"poi_id\":3910,\"poi_name\":\"泰国\",\"destination_entry_type\":3}","h5NavUrl":"https://m.tuniu.com/g3910/guide-0-0/","pcUrl":"http://www.tuniu.com/g3910/guide-0-0/"},{"poiId":45398,"poiName":"曼谷","appNavUrl":"tuniuapp://page?iosPageName=TNDestinationStationViewController&androidPageName=com.tuniu.app.ui.activity.DestinationStationActivity¶meters={\"poi_id\":45398,\"poi_name\":\"曼谷\",\"destination_entry_type\":3}","h5NavUrl":"https://m.tuniu.com/g45398/guide-0-0/","pcUrl":"http://www.tuniu.com/g45398/guide-0-0/"}]
     */

    private int contentId;
    private int tripImgId;
    private String imgUrl;
    private int imgWidth;
    private int imgHeight;
    private String imgExif;
    private UserInfo userInfo;
    private String imgDesc;
    private boolean hasPraised;
    private String time;
    private String h5Url;
    private int hotIndex;
    private int praiseCnt;
    private int commentCnt;
    private int shareCnt;
    private int recommendFlag;
    private int poiType;
    private int destPoiId;
    private String destPoiName;
    private String createTime;
    private String fullTextPoi;
    private int currentType;
    private boolean excellent;
    private int state;
    private List<TagsData> tags;
    private List<PoiListData> poiList;

    public static DetailData objectFromData(String str) {

        return new Gson().fromJson(str, DetailData.class);
    }

    public static List<DetailData> arrayDetailsnullFromData(String str) {

        Type listType = new TypeToken<ArrayList<DetailData>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getTripImgId() {
        return tripImgId;
    }

    public void setTripImgId(int tripImgId) {
        this.tripImgId = tripImgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getImgExif() {
        return imgExif;
    }

    public void setImgExif(String imgExif) {
        this.imgExif = imgExif;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public boolean isHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(boolean hasPraised) {
        this.hasPraised = hasPraised;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public int getHotIndex() {
        return hotIndex;
    }

    public void setHotIndex(int hotIndex) {
        this.hotIndex = hotIndex;
    }

    public int getPraiseCnt() {
        return praiseCnt;
    }

    public void setPraiseCnt(int praiseCnt) {
        this.praiseCnt = praiseCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public int getShareCnt() {
        return shareCnt;
    }

    public void setShareCnt(int shareCnt) {
        this.shareCnt = shareCnt;
    }

    public int getRecommendFlag() {
        return recommendFlag;
    }

    public void setRecommendFlag(int recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    public int getPoiType() {
        return poiType;
    }

    public void setPoiType(int poiType) {
        this.poiType = poiType;
    }

    public int getDestPoiId() {
        return destPoiId;
    }

    public void setDestPoiId(int destPoiId) {
        this.destPoiId = destPoiId;
    }

    public String getDestPoiName() {
        return destPoiName;
    }

    public void setDestPoiName(String destPoiName) {
        this.destPoiName = destPoiName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFullTextPoi() {
        return fullTextPoi;
    }

    public void setFullTextPoi(String fullTextPoi) {
        this.fullTextPoi = fullTextPoi;
    }

    public int getCurrentType() {
        return currentType;
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
    }

    public boolean isExcellent() {
        return excellent;
    }

    public void setExcellent(boolean excellent) {
        this.excellent = excellent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<TagsData> getTags() {
        return tags;
    }

    public void setTags(List<TagsData> tags) {
        this.tags = tags;
    }

    public List<PoiListData> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<PoiListData> poiList) {
        this.poiList = poiList;
    }
}
