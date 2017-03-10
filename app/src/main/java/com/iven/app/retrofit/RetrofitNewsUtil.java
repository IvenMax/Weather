package com.iven.app.retrofit;

import android.util.Log;

import com.iven.app.utils.ApiConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Iven
 * @date 2017/3/5 10:54
 */

public class RetrofitNewsUtil {
    private static final String TAG = "zpy_RetrofitNewsUtil";
    private static final String BASE_URL = ApiConstants.NETEAST_HOST;
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit;

    //实例化私有
    private RetrofitNewsUtil() {

    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.addInterceptor(LoggingInterceptor);
            return new Retrofit.Builder().client(httpClientBuilder.build()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(BASE_URL).build();
        } else {
            return retrofit;
        }
    }

    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.e(TAG, "intercept: 73" + "行 = " + response.request().url());
            Log.i(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

//    public static Observable<ResponseBody> getNewsBodyHtmlPhoto(String photoPath) {
//        return mNewsServiceRequest.getNewsBodyHtmlPhoto(photoPath);
//    }

}
