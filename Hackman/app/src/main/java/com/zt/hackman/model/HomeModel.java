package com.zt.hackman.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.ActionDetailsActivity;
import com.zt.hackman.activity.NewsListActivity;
import com.zt.hackman.adapter.MovementAdapter;
import com.zt.hackman.adapter.NewsAdapter;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.base.widget.XLButton;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HomeResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.DisplayUtil;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.view.NetworkImageHolderView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class HomeModel implements OnItemClickListener,View.OnClickListener{
    public RelativeLayout titleBar;
    public ConvenientBanner banner;
    public GridView gridView;
    public ProgressActivity progressActivity;
    //public ListView recommendListView;
    private int current=1;


    public TextView moreNewsBtn,adapter_recommend_info,adapter_recommend_info_two;
    public ListView listView;   //新闻资讯视图
    public NewsAdapter newsAdapter;

    public Activity activity;

    StringCallback homeCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            progressActivity.showError("网络请求异常", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        progressActivity.showLoading();
                        initData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity,"JSON转换异常",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,activity);
            if(res.code>0){
                HomeResponse homeResponse = (HomeResponse) GsonUtils.jsonToBean(res.data,HomeResponse.class);
                //如果资讯信息为空
                if(homeResponse.latestInfo==null||homeResponse.latestInfo.size()<=0){
                    progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                progressActivity.showLoading();
                                initData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(activity,"JSON转换异常",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    startBanner(homeResponse.banner);
                    initNews(homeResponse.latestInfo,activity);
                    initMoveNews(homeResponse.newList);
                    XLButton leftImage = (XLButton)activity.findViewById(R.id.nav_left_btn);

                    if("0".equals(homeResponse.isRead)){
                        leftImage.setDrawable(R.mipmap.navi_message);
                    }else{
                        leftImage.setDrawable(R.mipmap.navi_message_have);
                    }
                    progressActivity.showContent();

                }

            }else{
                progressActivity.showError(res.msg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            progressActivity.showLoading();
                            initData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity,"JSON转换异常",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };

    public void findViewByIds(View view, final Activity activity){
        this.activity = activity;
        banner = (ConvenientBanner) view.findViewById(R.id.home_banner);
        gridView = (GridView)view.findViewById(R.id.recommendGridView);
        //recommendListView = (ListView)view.findViewById(R.id.recommendListView);
        listView = (ListView)view.findViewById(R.id.newsListView);
        titleBar = (RelativeLayout)view.findViewById(R.id.fragment_home_nav);
        moreNewsBtn = (TextView)view.findViewById(R.id.more_news_btn);
        adapter_recommend_info = (TextView)view.findViewById(R.id.adapter_recommend_info);
        adapter_recommend_info_two = (TextView)view.findViewById(R.id.adapter_recommend_info_second);

        progressActivity = (ProgressActivity)view.findViewById(R.id.fragment_home_progress);
        progressActivity.showLoading();
        titleBar.setBackgroundResource(R.color.colorWhite);
        moreNewsBtn.setOnClickListener(this);
    }

    public void initData() throws JSONException {
        HackRequest request = new HackRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command","homePage");
        jsonObject.put("page",""+current);
        jsonObject.put("limit",Constant.home_pageSize);
        String sid = PreferencesUtils.getSID(activity);
        if(sid!=null|| !StringUtils.isEmpty(sid)){
            jsonObject.put("sid",sid);
        }
        request.request(jsonObject.toString(),homeCallBack,Constant.TEST_HOST);
    }
    /**
     * 初始化banner
     * @param datas
     */
    public void startBanner(List<HomeResponse.Banner> datas){
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, datas)    //设置需要切换的View
            .setPointViewVisible(true)    //设置指示器是否可见
            .setPageIndicator(new int[]{R.mipmap.red_point, R.mipmap.blue_point})   //设置指示器圆点
            .startTurning(5000)     //设置自动切换（同时设置了切换时间间隔）
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT) //设置指示器位置（左、中、右）
            .setOnItemClickListener(this); //设置点击监听事件

    }

    /**
     * 初始化新闻资讯
     * @param newses
     * @param context
     */
    public void initNews(final List<HomeResponse.LatestInfo> newses, Context context){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(newses.size()*100));
        listView.setLayoutParams(params);
        newsAdapter = new NewsAdapter(context);
        listView.setAdapter(newsAdapter);
        newsAdapter.refreshData(newses);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(activity,ActionDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTNENTS.INTENT_ACTION_ID,newses.get(position).id);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 初始化推送信息
     */
    public void initMovement(MovementAdapter adapter){
        gridView.setAdapter(adapter);

    }

    /**
     * 初始化推送的消息
     */
    private void initMoveNews(List<HomeResponse.News> newses){
        if(newses!=null&&newses.size()==2) {
            adapter_recommend_info.setText(newses.get(0).title);
            adapter_recommend_info_two.setText(newses.get(1).title);
        }
    }

    public void clear(){
        banner = null;
        gridView = null;
        //recommendListView = null;
        listView = null;
    }
    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_news_btn:
                activity.startActivity(new Intent(activity, NewsListActivity.class));
                break;
        }
    }
}
