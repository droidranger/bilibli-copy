package com.ranger.xyg.xygapp.model.http;

import com.ranger.xyg.xygapp.bean.image.ResultData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by xyg on 2017/5/5.
 */

public interface TuniuTPCrossApi {
    @GET("tpa/image/browse/list")
    Call<ResultData> tuniuCross();
}
