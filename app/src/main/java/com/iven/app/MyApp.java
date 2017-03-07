package com.iven.app;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.logging.Level;

/**
 * @author Iven
 * @date 2017/2/21 15:25
 * @Description
 */

public class MyApp extends Application {
    public static String currentCity = "北京";

    @Override
    public void onCreate() {
        super.onCreate();
        initOkGoHttp();
        initUMeng();
    }

    private void initUMeng() {
        Config.DEBUG = true;//开启DEBUG模式
        UMShareAPI.get(this);
        {
            //Demo里边的
//            PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//            PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
//            PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
            //自己申请的
            PlatformConfig.setWeixin("wxdfeb3c5558c00924", "93fda80f3007be22e6c6ddc7787ad4b6");
            PlatformConfig.setSinaWeibo("2656078997", "80fae555d83800ded489e9a137a7f6d8", "http://sns.whalecloud.com");
//            PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        }
    }

    /**
     * 初始化OkGo网络请求框架
     */
    private void initOkGoHttp() {
        OkGo.init(this);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3).setCookieStore(new PersistentCookieStore());       //cookie持久化存储，如果cookie不过期，则一直有效

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
