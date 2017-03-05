package com.iven.app.retrofit.request;

import com.iven.app.bean.WeatherSuggestBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Iven
 * @date 2017/3/5 10:54
 */

public interface SuggestionRequest {

    @GET("suggestion")
    Observable<WeatherSuggestBean> getHeWeather(@Query("key") String key, @Query("city") String city);

}
