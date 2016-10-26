package com.qxsx.chaersi.driverclient.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by Chaersi on 16/7/1.
 */
public class BaseApplication extends Application {
    public static final String TAG = "BaseApplication";
    public static BaseApplication mInstance;
    public static Gson gson;
    private RequestQueue mRequestQueue;
    public String loginsid="";
    public Picasso built;

    public boolean ONLINE_STATE=false;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        built = builder.build();
        built.setLoggingEnabled(true);//是否开启debug日志
        Picasso.setSingletonInstance(built);
    }

    /************* 华丽丽的分割线 *************/
    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    /**
     * 获取请求队列
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(),new HurlStack());
        }
        return mRequestQueue;
    }

    /**
     * 添加请求队列
     * @param req 请求Request
     * @param tag Request添加tag
     * @param <T>
     */
    public <T> void addRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * 添加请求队列
     * @param req 请求Request
     * @param <T>
     */
    public <T> void addRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * 获取Cache
     * @return
     */
    public Cache getCache(){
        return getRequestQueue().getCache();
    }

    private String checkAppVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

}
