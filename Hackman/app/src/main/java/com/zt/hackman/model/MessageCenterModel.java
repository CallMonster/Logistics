package com.zt.hackman.model;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.OrderDetailClaimActivity;
import com.zt.hackman.adapter.AbsRVAdapter;
import com.zt.hackman.adapter.MessageAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.MessageResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.SystemUtils;
import com.zt.hackman.widget.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subcriber;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class MessageCenterModel {

    public RelativeLayout messageCenterNav;
    public ZrcListView recycleView;

    private int isFresh = 1;
    private int current = 1;
    MessageAdapter adapter;
     List<MessageResponse> messages;
    ProgressActivity progressActivity;
    private BaseActivity ac;

    public void findViewByIds(BaseActivity activity){
        this.ac = activity;
        recycleView = (ZrcListView)activity.findViewById(R.id.loadMoreView);
        messageCenterNav = (RelativeLayout) activity.findViewById(R.id.message_center_nav);
        progressActivity = (ProgressActivity)activity.findViewById(R.id.message_list_progressActivity);

        messageCenterNav.setBackgroundResource(R.color.colorWhite);
    }

    @Subcriber
    public void onEventMain(RefreshEvent refreshEvent){
        progressActivity.showLoading();
        isFresh = 1;
        current = 1;
        initData();
    }

    public void init(Activity activity){
        isFresh = 1;
        current = 1;
        SystemUtils.initListView(ac,recycleView);
        recycleView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                ac.startActivity(OrderDetailClaimActivity.class);
            }
        });
        // 下拉刷新事件回调（可选）
        recycleView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        recycleView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        initData();
    }

    /**
     * 刷新
     */
    private void refresh(){
        isFresh = 1;
        current = 1;
        initData();
    }

    /**
     * 加载更多
     */
    private void loadMore(){
        current++;
        isFresh =0;
        initData();
    }

    StringCallback listCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            progressActivity.showError("网络请求异常", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                //loadMore.setLoading(false);
                String content = null;
                try {
                    JSONObject jsonObject = new JSONObject(res.data);
                    content = jsonObject.getString("msgList");
                } catch (JSONException e) {
                    e.printStackTrace();
                    recycleView.setRefreshFail();
                    Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
                }
                List<MessageResponse> datas = (List<MessageResponse>) GsonUtils.jsonToList(content
                        ,new TypeToken<List<MessageResponse>>(){}.getType());

                if(isFresh==1){
                    messages = datas;
                    recycleView.setRefreshSuccess("加载成功"); // 通知加载成功
                    recycleView.startLoadMore();
                }else{
                    messages.addAll(datas);
                    recycleView.setLoadMoreSuccess();
                }
                if(datas.size()<Constant.home_pageSize){
                    recycleView.stopLoadMore();
                }
                if(messages==null||messages.size()<=0){
                    progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            current = 1;
                            isFresh = 1;
                            progressActivity.showLoading();
                            initData();
                        }
                    });
                }else {
                    if(adapter==null) {
                        adapter = new MessageAdapter(ac);
                        recycleView.setAdapter(adapter);
                    }
                    adapter.refreshData(messages);
                    progressActivity.showContent();
                }
            }else{
                if(res.code==-10){
                    LoginUtils.toLogin(ac);
                }else {
                    progressActivity.showError(res.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressActivity.showLoading();
                            current = 1;
                            isFresh = 1;
                            initData();
                        }
                    });
                }
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","centerOfMessage");
            String sid = PreferencesUtils.getSID(ac);
            if(sid==null){
                LoginUtils.showDialog(ac,"提示登录","你尚未登录，请登录后访问");
            }
            jsonObject.put("sid",sid);
            jsonObject.put("page",""+current);
            jsonObject.put("perPage",""+Constant.order_pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),listCallBack,Constant.TEST_HOST);
    }

}
