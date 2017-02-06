package com.junyuzhou.jywallpaper.api;

import com.junyuzhou.jywallpaper.model.RandomImage;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Junyu on 2017-01-29.
 */

public interface ImageService {
    @GET("random")
    Observable<RandomImage> getImg(
            @Query("client_id") String clientId,
            @Query("w") String imgWidth,
            @Query("h") String imgHeight,
            @Query("orientation") String orientation
    );
}
