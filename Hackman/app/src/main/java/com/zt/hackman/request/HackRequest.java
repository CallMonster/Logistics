package com.zt.hackman.request;

import com.lzy.imagepicker.bean.ImageItem;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zt.hackman.callback.LoginCallBack;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.response.HackmanResponse;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/8.
 */
public class HackRequest {

    public void request(String param,StringCallback callBack,String url){
        OkHttpUtils
                .post()
                .url(url)
                .addParams("data",param)
                .build()
                .execute(callBack);
    }

    public void requestFileAndParams(String params, ArrayList<HackmanResponse.FileParams> fileParamses, String url, StringCallback callback){
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("data",params);
        if(fileParamses!=null&&fileParamses.size()>0){
            for (HackmanResponse.FileParams fileParams : fileParamses){
                builder.addFile(fileParams.name,fileParams.file.getName(),fileParams.file);
            }
        }
        builder.build().execute(callback);
    }

}
