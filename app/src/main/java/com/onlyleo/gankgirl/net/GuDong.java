/*
 *
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.onlyleo.gankgirl.net;

import com.onlyleo.gankgirl.model.GankData;
import com.onlyleo.gankgirl.model.PrettyGirlData;
import com.onlyleo.gankgirl.model.VideoData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GuDong {

    @GET("/data/福利/{pagesize}/{page}")
    Observable<PrettyGirlData> getPrettyGirlData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("/data/休息视频/{pagesize}/{page}")
    Observable<VideoData> getVideoData(@Path("pagesize") int pagesize, @Path("page") int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
