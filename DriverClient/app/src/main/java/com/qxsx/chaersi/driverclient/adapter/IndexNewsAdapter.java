package com.qxsx.chaersi.driverclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.entry.result.IndexResult;
import com.qxsx.chaersi.driverclient.entry.result.IndexResult.DataBean.LatestInfoBean;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chaersi on 16/10/24.
 */
public class IndexNewsAdapter extends RecyclerView.Adapter<IndexNewsAdapter.ItemViewHolder>{

    private Context context;
    private List<LatestInfoBean> newsArr;

    private Picasso picasso;
    public IndexNewsAdapter(Context context,List<LatestInfoBean> newsArr){
        this.context=context;
        this.newsArr=newsArr;
        picasso = BaseApplication.getInstance().built;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_indexnews, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.position=position;

        holder.titleView.setText(newsArr.get(position).getTitle());
        holder.contentView.setText(newsArr.get(position).getContent());
        picasso.with(context)
                .load(newsArr.get(position).getSmallImg())
                .tag(newsArr.get(position).getSmallImg())
                .placeholder(R.drawable.loading)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .error(R.drawable.imgerr)
                .into(holder.newsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.newsImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return newsArr.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView newsImage;
        public TextView titleView;
        public TextView contentView;
        public RelativeLayout parentPanel;

        public int position;
        public ItemViewHolder(View itemView) {
            super(itemView);
            newsImage= (ImageView) itemView.findViewById(R.id.news_img);
            titleView= (TextView) itemView.findViewById(R.id.news_title);
            contentView= (TextView) itemView.findViewById(R.id.news_content);
            parentPanel= (RelativeLayout) itemView.findViewById(R.id.parentPanel);

        }
    }



}

