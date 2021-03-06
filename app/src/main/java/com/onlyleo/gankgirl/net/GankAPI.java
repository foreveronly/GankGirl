
package com.onlyleo.gankgirl.net;

import com.onlyleo.gankgirl.model.CategoryData;
import com.onlyleo.gankgirl.model.ContentData;
import com.onlyleo.gankgirl.model.GankData;
import com.onlyleo.gankgirl.model.PrettyGirlData;
import com.onlyleo.gankgirl.model.SearchData;
import com.onlyleo.gankgirl.model.entity.Version;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GankAPI {

    @GET("data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("history/content/{pagesize}/{page}")
    Observable<ContentData> getContentData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("data/{category}/{pagesize}/{page}")
    Observable<CategoryData> getCategoryData(@Path("category") String category, @Path("pagesize") int pagesize, @Path("page") int page);

    @GET("search/query/{query}/category/{category}/count/{count}/page/{page}")
    Observable<SearchData> getSearchData(@Path("query") String query, @Path("category") String category, @Path("count") int count, @Path("page") int page);

    @GET("{appId}")
    Observable<Version> getVersionInfo(@Path("appId") String appId, @Query("token") String token);

}
