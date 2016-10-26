package com.qxsx.chaersi.driverclient.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.adapter.IndexNewsAdapter;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.BaseFragment;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.IndexReq;
import com.qxsx.chaersi.driverclient.entry.result.IndexResult;
import com.qxsx.chaersi.driverclient.entry.result.IndexResult.DataBean.NewListBean;
import com.qxsx.chaersi.driverclient.entry.result.IndexResult.DataBean.LatestInfoBean;
import com.qxsx.chaersi.driverclient.entry.result.IndexResult.DataBean.BannerBean;
import com.qxsx.chaersi.driverclient.widget.NetworkImageHolderView;
import com.qxsx.chaersi.driverclient.widget.ShowAllLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chaersi on 16/10/23.
 */
public class IndexFragment extends BaseFragment {
    private String TAG = "IndexFragment";

    @BindView(R.id.leftView) ImageView leftView;
    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.convenientBanner) ConvenientBanner convenientBanner;
    @BindView(R.id.moreNewsBtn) TextView moreNewsBtn;
    @BindView(R.id.showAllItem) RecyclerView showAllItem;
    @BindView(R.id.msg1) TextView msg1View;
    @BindView(R.id.msg2) TextView msg2View;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_index, null);
        ButterKnife.bind(this, view);

        showProgressDialog("加载中..");
        leftView.setImageResource(R.mipmap.navi_message_have);
        title.setBackgroundResource(R.mipmap.logo);

        BaseApplication.getInstance().addRequestQueue(indexInfoReq);
        return view;
    }

    @OnClick({R.id.leftBtn, R.id.moreNewsBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                break;
            case R.id.moreNewsBtn:
                break;
        }
    }

    StringRequest indexInfoReq = new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "index:"+response);
                    IndexResult result=BaseApplication.gson.fromJson(response,IndexResult.class);
                    if("1".equals(result.getCode())){
                        List<BannerBean> imgs=result.getData().getBanner();
                        ArrayList<String> imageArr=new ArrayList<>();
                        for(BannerBean item:imgs){
                            imageArr.add(item.getBigImg());
                        }
                        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                            @Override
                            public NetworkImageHolderView createHolder() {
                                return new NetworkImageHolderView();
                            }
                        },imageArr);
                        convenientBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator,
                                R.drawable.ic_page_indicator_focused});
                        convenientBanner.startTurning(5000);

                        List<NewListBean> newsArr=result.getData().getNewList();
                        for(int i=0;i<newsArr.size();i++){
                            if(i==0){
                                msg1View.setText(newsArr.get(i).getTitle());
                            }else{
                                msg2View.setText(newsArr.get(i).getTitle());
                            }
                        }

                        showAllItem.setHasFixedSize(true);
                        ShowAllLinearLayoutManager layoutManager = new ShowAllLinearLayoutManager(getActivity());
                        showAllItem.setLayoutManager(layoutManager);
                        List<LatestInfoBean> tempArr=result.getData().getLatestInfo();
                        Log.i(TAG,"item个数"+tempArr.size());
                        IndexNewsAdapter newsAdapter=new IndexNewsAdapter(getActivity(),tempArr);
                        showAllItem.setAdapter(newsAdapter);

                    }else{
                        showTips(result.getMsg());
                    }
                    hideProgressDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showTips("网络不给力");
                    hideProgressDialog();
                }
            }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            IndexReq indexReq = new IndexReq();
            indexReq.setCommand("homePage");
            indexReq.setSid(BaseApplication.getInstance().loginsid);
            indexReq.setLimit("3");
            indexReq.setPage("1");
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(indexReq));
            return params;
        }
    };


}
