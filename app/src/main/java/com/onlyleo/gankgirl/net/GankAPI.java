
package com.onlyleo.gankgirl.net;

import com.onlyleo.gankgirl.model.ContentData;
import com.onlyleo.gankgirl.model.GankData;
import com.onlyleo.gankgirl.model.PrettyGirlData;
import com.onlyleo.gankgirl.model.VideoData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankAPI {

    @GET("data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("data/休息视频/{pagesize}/{page}")
    Observable<VideoData> getVideoData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("history/content/{pagesize}/{page}")
    Observable<ContentData> getContentData(@Path("pagesize") int pagesize, @Path("page") int page);
}
