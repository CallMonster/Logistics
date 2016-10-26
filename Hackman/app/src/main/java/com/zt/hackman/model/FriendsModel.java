package com.zt.hackman.model;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.adapter.FriendsAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.FriendsResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class FriendsModel  {
    public ListView friendsListView;
    public RelativeLayout navLayout;
    private BaseActivity ac;
    private FriendsAdapter adapter;

    List<FriendsResponse> list;

    public void findViewByIds(BaseActivity activity){
        this.ac = activity;
        navLayout = (RelativeLayout)activity.findViewById(R.id.invent_friends_nav);
        friendsListView = (ListView)activity.findViewById(R.id.friends_list_view);
    }

    /**
     * 初始化
     */
    public void init(){
        navLayout.setBackgroundResource(R.color.colorWhite);
        list = new ArrayList<FriendsResponse>();
        adapter = new FriendsAdapter(ac);
        friendsListView.setAdapter(adapter);
        initFriends();
        if(list!=null&&list.size()>0){
            adapter.refreshData(list);
        }else{
            Toast.makeText(ac,"暂无联系人信息",Toast.LENGTH_SHORT).show();
        }
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                invent(list.get(position));
            }
        });
    }

    StringCallback inventCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac, res.msg);
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void invent(FriendsResponse friends){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","invite");
            jsonObject.put("sid","daefhgherr45456");
            jsonObject.put("tel",friends.phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new HackRequest().request(jsonObject.toString(),inventCallBack, Constant.TEST_HOST);
    }



    /**
     * 初始化本地联系人信息
     */
    private void initFriends(){
        // 查询联系人
        Cursor contactsCursor = ac.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        // 姓名的索引
        int nameIndex = 0;
        // 联系人姓名
        String name = null;
        // 联系人头像ID
        String photoId = null;
        // 联系人的ID索引值
        String contactsId = null;
        // 查询联系人的电话号码
        Cursor phoneCursor = null;
        while (contactsCursor.moveToNext()) {
            nameIndex = contactsCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            name = contactsCursor.getString(nameIndex);
            photoId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_ID));
            contactsId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));
            phoneCursor = ac.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactsId, null, null);
            // 遍历联系人号码（一个人对应于多个电话号码）
            while (phoneCursor.moveToNext()) {
                FriendsResponse response = new FriendsResponse();
               // HashMap<String, String> phoneMap = new HashMap<String, String>();
                // 添加联系人姓名
                response .name = name;
                response.image = photoId;
                response.phone=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                list.add(response);
            }
            phoneCursor.close();
        }
        contactsCursor.close();
    }

    private void initData(){

    }
}
