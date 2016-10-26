package com.zt.hackman.response;

import android.content.Context;
import android.widget.Toast;

import com.zt.hackman.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/10/9.
 */
public class Response {
    public int code;
    public String data;
    public String msg;

    public Response(String response, Context context){
        try {
            JSONObject object = new JSONObject(response);
            code = object.getInt("code");
            data = object.getString("data");
            String message = object.getString("msg");
            if(message!=null&& !StringUtils.isBlank(message)){
                msg = URLDecoder.decode(message,"utf-8");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"响应数据格式有误",Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(context,"响应数据格式有误",Toast.LENGTH_SHORT).show();
        }
    }

}
