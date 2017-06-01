package com.ranger.xyg.xygapp.demos.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xyg on 2017/5/5.
 */

public interface GitHubApi {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributorsBySimpleGetCall(@Path("owner") String owner, @Path("repo") String repo);
}
