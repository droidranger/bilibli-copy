package com.ranger.xyg.xygapp.bean.image;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyg on 2017/6/14.
 */

class PoiListData {
    /**
     * poiId : 3910
     * poiName : 泰国
     * appNavUrl : tuniuapp://page?iosPageName=TNDestinationStationViewController&androidPageName=com.tuniu.app.ui.activity.DestinationStationActivity¶meters={"poi_id":3910,"poi_name":"泰国","destination_entry_type":3}
     * h5NavUrl : https://m.tuniu.com/g3910/guide-0-0/
     * pcUrl : http://www.tuniu.com/g3910/guide-0-0/
     */

    private int poiId;
    private String poiName;
    private String appNavUrl;
    private String h5NavUrl;
    private String pcUrl;

    public static PoiListData objectFromData(String str) {

        return new Gson().fromJson(str, PoiListData.class);
    }

    public static List<PoiListData> arrayPoiListnullFromData(String str) {

        Type listType = new TypeToken<ArrayList<PoiListData>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
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

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }
}
