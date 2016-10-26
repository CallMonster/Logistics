package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.ActionDetailsActivity;
import com.zt.hackman.adapter.AbsRVAdapter;
import com.zt.hackman.adapter.NewsAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HomeResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.SystemUtils;
import com.zt.hackman.widget.SimpleFooter;
import com.zt.hackman.widget.SimpleHeader;
import com.zt.hackman.widget.ZrcAbsListView;
import com.zt.hackman.widget.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.LastOwnerException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class NewsListModel{
    public RelativeLayout navLayout;
    private BaseActivity activity;
    public ProgressActivity progressActivity;
    private int current = 1;
    ZrcListView listView;
    private NewsAdapter adapter;
    List<HomeResponse.LatestInfo> latestInfos;
    private int isFresh = 1;

    StringCallback listCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            progressActivity.showError("网络请求异常", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressActivity.showLoading();
                    initData();
                }
            });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,activity);
            if (res.code > 0) {
                String content = null;
                try {
                    JSONObject array = new JSONObject(res.data);
                    content = array.getString("latestInfo");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity,"JSON格式转换异常",Toast.LENGTH_SHORT).show();
                }
                List<HomeResponse.LatestInfo> latestInfoses = (List<HomeResponse.LatestInfo>)
                        GsonUtils.jsonToList(content,
                                new TypeToken<List<HomeResponse.LatestInfo>>(){}.getType());
                if(isFresh==1){
                    latestInfos = latestInfoses;
                    listView.setRefreshSuccess("加载成功"); // 通知加载成功
                    listView.startLoadMore();
                }else{
                    latestInfos.addAll(latestInfoses);
                    listView.setLoadMoreSuccess();
                }
                if(latestInfoses.size()<Constant.home_pageSize){
                    listView.stopLoadMore();
                }
                if(latestInfos==null||latestInfos.size()<=0){
                    progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressActivity.showLoading();
                            initData();
                        }
                    });
                }else {


                    if (adapter == null) {
                        //initAadapter(latestInfos);
                        adapter = new NewsAdapter(activity);
                        listView.setAdapter(adapter);
                    }
                        adapter.refreshData(latestInfos);
                    progressActivity.showContent();
                }

            }else{
                if(res.code==-10){
                    LoginUtils.toLogin(activity);
                }else {
                    progressActivity.showError(res.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressActivity.showLoading();
                            initData();
                        }
                    });
                }

            }
        }
    };


    public void findViewByIds(BaseActivity activity){
        this.activity =activity;
        listView = (ZrcListView) activity.findViewById(R.id.loadMoreView);
        navLayout = (RelativeLayout)activity.findViewById(R.id.news_list_nav);
        progressActivity = (ProgressActivity)activity.findViewById(R.id.news_list_progressActivity);

        navLayout.setBackgroundResource(R.color.colorWhite);
    }

    public void init(final Activity activity){
        SystemUtils.initListView(activity,listView);
        progressActivity.showLoading();
        current = 1;
        isFresh = 1;
        // 下拉刷新事件回调（可选）
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });

        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                HomeResponse.LatestInfo latestInfo = latestInfos.get(position);
                if(latestInfo!=null){
                    Intent intent = new Intent(activity,ActionDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.INTNENTS.INTENT_ACTION_ID,latestInfo.id);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            }
        });
        initData();


    }

    private void initView(){

        //listView.refresh(); // 主动下拉刷新
    }

    private void initData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","getInformation");
            jsonObject.put("page",""+current);
            jsonObject.put("limit",Constant.home_pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HackRequest().request(jsonObject.toString(),listCallBack,Constant.TEST_HOST);

    }


    public void clear(){
        //loadMore=null;
        latestInfos = null;
        listView=null;
        adapter = null;
        navLayout = null;
        progressActivity =null;
    }


    private void refresh() {
        adapter = null;
        current = 1;
        isFresh = 1;
        initData();
    }

    private void loadMore() {
        current++;
        isFresh = 0;
        initData();
    }
}
